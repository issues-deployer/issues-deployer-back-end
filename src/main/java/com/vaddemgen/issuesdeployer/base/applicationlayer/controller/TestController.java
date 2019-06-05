package com.vaddemgen.issuesdeployer.base.applicationlayer.controller;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.client.gitlab.businesslayer.service.GitClient;
import java.io.IOException;
import java.util.stream.Stream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  private final GitClient gitClient;

  public TestController(GitClient gitClient) {
    this.gitClient = gitClient;
  }

  @RequestMapping("test")
  public Stream<Issue> findIssues() throws IOException, InterruptedException {
    return gitClient.findIssues();
  }

  @RequestMapping("project")
  public Stream<Project> findProjects() throws IOException, InterruptedException {
    return gitClient.findProjects();
  }
}
