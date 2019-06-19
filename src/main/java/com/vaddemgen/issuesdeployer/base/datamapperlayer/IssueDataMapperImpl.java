package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClientFactory;
import java.io.IOException;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public final class IssueDataMapperImpl implements IssueDataMapper {

  private final GitClientFactory gitClientFactory;

  public IssueDataMapperImpl(GitClientFactory gitClientFactory) {
    this.gitClientFactory = gitClientFactory;
  }

  @Override
  public Stream<Issue> findIssuesByProject(GitAccount gitAccount, Project project) {
    var client = gitClientFactory.createGitClient(gitAccount);

    try {
      return client.findIssues(project);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    return Stream.empty();
  }
}
