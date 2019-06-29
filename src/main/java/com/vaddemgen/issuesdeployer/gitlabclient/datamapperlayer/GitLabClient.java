package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer;

import static java.net.http.HttpRequest.BodyPublishers.noBody;
import static java.util.Collections.emptyList;

import com.google.gson.Gson;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.AbstractGitClient;
import com.vaddemgen.issuesdeployer.gitlabclient.businesslayer.GitLabAccount;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.factory.IssueFactory;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.factory.ProjectFactory;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.factory.SubGroupFactory;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.factory.SuperGroupFactory;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.GitLabGroupDto;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.GitLabIssueDto;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.GitLabProjectDto;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.PaginatorDto;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.PaginatorDto.NextPageDetails;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import org.apache.http.HttpStatus;

/**
 * DAO (Client) for the GitLab Server.
 */
public final class GitLabClient extends AbstractGitClient<GitLabAccount> {

  private static final String GIT_LAB_API_URL = "https://gitlab.com/api/v4";

  private static final String RESOURCE_GROUPS = "groups";
  private static final String RESOURCE_PROJECTS = "projects/?membership=true&simple=true";
  private static final String RESOURCE_ISSUES = "projects/%s/issues/"
      + "?order_by=updated_at&sort=desc&state=opened";

  private static final Duration GROUPS_TTL = Duration.ofMinutes(5);
  private static final Duration PROJECTS_TTL = Duration.ofMinutes(5);

  private static final HttpClient httpClient = HttpClient.newBuilder().build();

  private final GitLabCacheManager cacheManager;

  public GitLabClient(GitLabAccount gitAccount, GitLabCacheManager cacheManager) {
    super(gitAccount);
    this.cacheManager = cacheManager;
  }

  @Override
  public Stream<SuperGroup> findSuperGroups() throws IOException, InterruptedException {
    return findGroups()
        .stream()
        .filter(groupDto -> groupDto.getParentId().isEmpty())
        .map(SuperGroupFactory::createSuperGroup);
  }

  @Override
  public Stream<SubGroup> findSubGroups(SuperGroup superGroup)
      throws IOException, InterruptedException {
    return findGroups()
        .stream()
        .filter(groupDto ->
            groupDto.getParentId()
                .map(parentId -> parentId == superGroup.getRemoteId())
                .orElse(false)
        )
        .map(SubGroupFactory::createSubGroup);
  }

  private synchronized List<GitLabGroupDto> findGroups() throws IOException, InterruptedException {
    var optional = cacheManager.getCachedGroups(gitAccount);
    List<GitLabGroupDto> projects;
    if (optional.isPresent()) {
      projects = optional.get();
    } else {
      projects = loadData(RESOURCE_GROUPS, GitLabGroupDto[].class);
      cacheManager.cacheGroups(gitAccount, projects, GROUPS_TTL);
    }
    return projects;
  }

  @Override
  public Stream<Project> findProjects(Group group)
      throws IOException, InterruptedException {
    List<GitLabProjectDto> projects;

    synchronized (new Object()) {
      var optional = cacheManager.getCachedProjects(gitAccount);

      if (optional.isPresent()) {
        projects = optional.get();
      } else {
        projects = loadData(RESOURCE_PROJECTS, GitLabProjectDto[].class);
        cacheManager.cacheProjects(gitAccount, projects, PROJECTS_TTL);
      }
    }

    return projects.stream()
        .filter(projectDto -> projectDto.getNamespace().getRemoteId() == group.getRemoteId())
        .map(ProjectFactory::createProject);
  }

  @Override
  public Stream<Issue> findIssues(Project project) throws IOException, InterruptedException {
    return loadData(String.format(RESOURCE_ISSUES, project.getRemoteId()), GitLabIssueDto[].class)
        .stream()
        .map(IssueFactory::createIssue);
  }

  private <T> List<T> loadData(String urlSegment, Class<T[]> clazz)
      throws IOException, InterruptedException {
    HttpRequest httpRequest = createHttpHeadRequest(urlSegment, gitAccount.getToken());
    HttpResponse<String> response = httpClient.send(httpRequest, BodyHandlers.ofString());

    if (response.statusCode() != HttpStatus.SC_OK) {
      return emptyList();
    }

    HttpHeaders headers = response.headers();

    PaginatorDto paginator = new PaginatorDto(
        headers.firstValue("X-Total-Pages").map(Integer::parseInt).orElseThrow(),
        headers.firstValue("X-Per-Page").map(Integer::parseInt).orElseThrow()
    );

    Iterator<NextPageDetails> iterator = paginator.iterator();

    List<T> result = new ArrayList<>();

    while (iterator.hasNext()) {
      NextPageDetails nextPageDetails = iterator.next();
      httpRequest = createHttpGetRequest(
          urlSegment
              + (urlSegment.contains("?") ? "" : "?")
              + (urlSegment.contains("&") ? "&" : "")
              + "per_page="
              + nextPageDetails.getPerPage()
              + "&page="
              + nextPageDetails.getNextPage(),
          gitAccount.getToken()
      );
      response = httpClient.send(httpRequest, BodyHandlers.ofString());

      if (response.statusCode() == HttpStatus.SC_OK) {
        result.addAll(List.of(new Gson().fromJson(response.body(), clazz)));
      }

      // TODO: Handle not 200 response.
    }

    return result;
  }

  /**
   * Creates HTTP request to GitLab API.
   *
   * @param urlSegment The url segment to be added
   * @param token      The GitLab private token
   */
  private HttpRequest createHttpGetRequest(String urlSegment, String token) {
    return HttpRequest.newBuilder()
        .uri(URI.create(GIT_LAB_API_URL + "/" + urlSegment))
        .timeout(Duration.ofMinutes(1))
        .header("PRIVATE-TOKEN", token)
        .GET()
        .build();
  }

  private HttpRequest createHttpHeadRequest(String urlSegment, String token) {
    return HttpRequest.newBuilder()
        .uri(URI.create(GIT_LAB_API_URL + "/" + urlSegment))
        .timeout(Duration.ofMinutes(1))
        .header("PRIVATE-TOKEN", token)
        .method("HEAD", noBody())
        .build();
  }
}
