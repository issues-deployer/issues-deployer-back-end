package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer;

import com.google.gson.Gson;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClient;
import com.vaddemgen.issuesdeployer.gitlabclient.businesslayer.GitLabAccount;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.factory.SubGroupFactory;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.factory.SuperGroupFactory;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.GitLabGroupDto;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;
import org.apache.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

/**
 * DAO (Client) for the GitLab Server.
 */
public final class GitLabClient implements GitClient<GitLabAccount> {

  private static final String GIT_LAB_API_URL = "https://gitlab.com/api/v4";
  private static final String RESOURCE_GROUPS = "groups";
  private static final Duration GROUPS_TTL = Duration.ofHours(1);

  private static final HttpClient httpClient = HttpClient.newBuilder().build();

  private final GitLabAccount gitAccount;
  private final GitLabCacheManager cacheManager;

  public GitLabClient(@NotNull GitLabAccount gitAccount, @NotNull GitLabCacheManager cacheManager) {
    this.gitAccount = gitAccount;
    this.cacheManager = cacheManager;
  }

  @Override
  public Stream<SuperGroup> findSuperGroups() {
    return cacheManager.getCachedGroups(gitAccount)
        .map(List::stream)
        .orElseGet(this::loadGroups)
        .filter(groupDto -> groupDto.getParentId().isEmpty())
        .map(groupDto -> SuperGroupFactory.createSuperGroup(groupDto, gitAccount));
  }

  @Override
  public Stream<SubGroup> findSubGroups(@NotNull SuperGroup superGroup)
      throws IOException, InterruptedException {
    return cacheManager.getCachedGroups(gitAccount)
        .map(List::stream)
        .orElseGet(this::loadGroups)
        .filter(groupDto ->
            groupDto.getParentId()
                .map(parentId -> parentId == superGroup.getRemoteId())
                .orElse(false)
        )
        .map(SubGroupFactory::createSubGroup);
  }

  /**
   * Loads groups by GitLab API.
   */
  private Stream<GitLabGroupDto> loadGroups() {
    HttpRequest httpRequest = createHttpRequest(RESOURCE_GROUPS, gitAccount.getToken());

    HttpResponse<String> response;
    try {
      response = httpClient.send(httpRequest, BodyHandlers.ofString());
    } catch (Exception e) {
      // TODO: handle this exception.
      throw new RuntimeException(e);
    }

    if (response.statusCode() == HttpStatus.SC_OK) {
      return bodyToDtoStream(response);
    }

    // TODO: Handle not 200 response.

    return Stream.empty();
  }

  /**
   * Parses the response body to DTOs.
   */
  private Stream<GitLabGroupDto> bodyToDtoStream(@NotNull HttpResponse<String> response) {
    var groups = List.of(new Gson().fromJson(response.body(), GitLabGroupDto[].class));
    cacheManager.cacheGroups(gitAccount, groups, GROUPS_TTL);
    return groups.stream();
  }

  /**
   * Creates HTTP request to GitLab API.
   *
   * @param urlSegment The url segment to be added
   * @param token      The GitLab private token
   */
  private HttpRequest createHttpRequest(@NotNull String urlSegment, @NotNull String token) {
    return HttpRequest.newBuilder()
        .uri(URI.create(GIT_LAB_API_URL + "/" + urlSegment))
        .timeout(Duration.ofMinutes(1))
        .header("PRIVATE-TOKEN", token)
        .GET()
        .build();
  }

//  @Override
//  public Stream<Issue> findIssues() throws IOException, InterruptedException {
//    HttpRequest httpRequest = HttpRequest.newBuilder()
//        .uri(URI.create("https://gitlab.com/api/v4/issues"))
//        .timeout(Duration.ofMinutes(1))
//        .header("PRIVATE-TOKEN", personalAccessToken)
//        .GET()
//        .build();
//
//    HttpResponse<String> response = httpClient.send(httpRequest, BodyHandlers.ofString());
//
//    if (response.statusCode() == HttpStatus.SC_OK) {
//      GitLabIssueDto[] gitLabIssueDto = new GsonBuilder()
//          .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//          .create()
//          .fromJson(response.body(), GitLabIssueDto[].class);
//
//      System.out.println(Arrays.toString(gitLabIssueDto));
//    }
//
//    System.out.println(response);
//
//    // curl -v -H 'PRIVATE-TOKEN: uuTciEGmcQPJjHuUwypb' 'https://gitlab.com/api/v4/groups'
//
//    return null;
//  }
//
//  @Override
//  public Stream<Project> findProjects() throws IOException, InterruptedException {
//
//    HttpRequest httpRequest = HttpRequest.newBuilder()
//        .uri(URI.create(
//            "https://gitlab.com/api/v4/projects?membership=true&simple=true&order_by=last_activity_at&sort=desc"))
//        .timeout(Duration.ofMinutes(1))
//        .header("PRIVATE-TOKEN", personalAccessToken)
//        .GET()
//        .build();
//
//    HttpClient httpClient = HttpClient.newBuilder().build();
//
//    HttpResponse<String> response = httpClient.send(httpRequest, BodyHandlers.ofString());
//
//    if (response.statusCode() == HttpStatus.SC_OK) {
//      GitLabIssueDto[] gitLabIssueDto = new GsonBuilder()
//          .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//          .create()
//          .fromJson(response.body(), GitLabIssueDto[].class);
//
//      System.out.println(Arrays.toString(gitLabIssueDto));
//    }
//
//    System.out.println(response);
//    return null;
//  }
}
