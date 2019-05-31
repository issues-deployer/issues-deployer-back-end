package com.vaddemgen.issuesdeployer.client.gitlab.businesslayer.service;

import com.vaddemgen.issuesdeployer.client.gitlab.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.client.gitlab.businesslayer.model.Project;
import java.io.IOException;
import java.util.stream.Stream;

public interface GitClient {

  Stream<Issue> findIssues() throws IOException, InterruptedException;

  Stream<Project> findProjects() throws IOException, InterruptedException;
}
