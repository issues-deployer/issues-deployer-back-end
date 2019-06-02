package com.vaddemgen.issuesdeployer.client.gitlab.businesslayer.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.client.gitlab.businesslayer.model.GitLabIssueDto;
import com.vaddemgen.issuesdeployer.client.gitlab.businesslayer.model.Project;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Stream;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public final class GitLabClient implements GitClient {

  private final String personalAccessToken = "uuTciEGmcQPJjHuUwypb";

  @Override
  public Stream<Issue> findIssues() throws IOException, InterruptedException {
    HttpRequest httpRequest = HttpRequest.newBuilder()
        .uri(URI.create("https://gitlab.com/api/v4/issues"))
        .timeout(Duration.ofMinutes(1))
        .header("PRIVATE-TOKEN", personalAccessToken)
        .GET()
        .build();

    HttpClient httpClient = HttpClient.newBuilder().build();

    HttpResponse<String> response = httpClient.send(httpRequest, BodyHandlers.ofString());

    if (response.statusCode() == HttpStatus.SC_OK) {
      GitLabIssueDto[] gitLabIssueDto = new GsonBuilder()
          .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
          .create()
          .fromJson(response.body(), GitLabIssueDto[].class);

      System.out.println(Arrays.toString(gitLabIssueDto));
    }

    System.out.println(response);

    // curl -v -H 'PRIVATE-TOKEN: uuTciEGmcQPJjHuUwypb' 'https://gitlab.com/api/v4/groups'

    return null;
  }

  @Override
  public Stream<Project> findProjects() throws IOException, InterruptedException {

    HttpRequest httpRequest = HttpRequest.newBuilder()
        .uri(URI.create(
            "https://gitlab.com/api/v4/projects?membership=true&simple=true&order_by=last_activity_at&sort=desc"))
        .timeout(Duration.ofMinutes(1))
        .header("PRIVATE-TOKEN", personalAccessToken)
        .GET()
        .build();

    HttpClient httpClient = HttpClient.newBuilder().build();

    HttpResponse<String> response = httpClient.send(httpRequest, BodyHandlers.ofString());

    if (response.statusCode() == HttpStatus.SC_OK) {
      GitLabIssueDto[] gitLabIssueDto = new GsonBuilder()
          .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
          .create()
          .fromJson(response.body(), GitLabIssueDto[].class);

      System.out.println(Arrays.toString(gitLabIssueDto));
    }

    System.out.println(response);
    return null;
  }
}
