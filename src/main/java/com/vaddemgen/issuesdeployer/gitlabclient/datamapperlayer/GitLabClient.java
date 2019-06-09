package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer;

import static java.util.stream.Collectors.partitioningBy;

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
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

/**
 * DAO (Client) for the GitLab Server.
 */
public final class GitLabClient implements GitClient<GitLabAccount> {

  private static final String GIT_LAB_API_URL = "https://gitlab.com/api/v4";

  private static final String RESOURCE_GROUPS = "groups";

  private static final HttpClient httpClient = HttpClient.newBuilder().build();

  private final GitLabAccount gitAccount;

  public GitLabClient(@NotNull GitLabAccount gitAccount) {
    this.gitAccount = gitAccount;
  }

  @Override
  public Stream<SuperGroup> findSuperGroups() throws IOException, InterruptedException {

    HttpRequest httpRequest = createHttpRequest(RESOURCE_GROUPS, gitAccount.getToken());

    HttpResponse<String> response = httpClient.send(httpRequest, BodyHandlers.ofString());

    if (response.statusCode() == HttpStatus.SC_OK) {
      return toDomainModel(bodyToDtoStream(response));
    }

    return Stream.empty();
  }

  private Stream<SuperGroup> toDomainModel(@NotNull Stream<GitLabGroupDto> dtoStream) {
    Map<Boolean, List<GitLabGroupDto>> listMap = dtoStream.collect(
        partitioningBy(dto -> dto.getParentId().isEmpty()));

    List<GitLabGroupDto> rawSubGroups = listMap.get(false);

    return listMap.get(true) // Super groups
        .stream()
        .map(SuperGroupFactory::createSuperGroup)
        .map(superGroup ->
            superGroup.clonePartially()
                .subGroups(findAndFillSubGroups(superGroup, rawSubGroups))
                .build()
        );
  }

  @NotNull
  private List<SubGroup> findAndFillSubGroups(@NotNull SuperGroup superGroup,
      @NotNull List<GitLabGroupDto> rawSubGroups) {
    return rawSubGroups.stream()
        .filter(dto -> dto.getParentId().isPresent())
        .filter(dto -> dto.getParentId().get() == superGroup.getId())
        .map(SubGroupFactory::createSubGroup)
        .collect(Collectors.toList());
  }

  private Stream<GitLabGroupDto> bodyToDtoStream(@NotNull HttpResponse<String> response) {
    return Stream.of(new Gson().fromJson(response.body(), GitLabGroupDto[].class));
  }

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
