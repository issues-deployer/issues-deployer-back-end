package com.vaddemgen.issuesdeployer.client.gitlab.businesslayer.service;

import com.vaddemgen.issuesdeployer.client.gitlab.businesslayer.model.Issue;
import java.io.IOException;
import java.util.stream.Stream;

public interface GitClient {

  Stream<Issue> findIssues() throws IOException, InterruptedException;
}
