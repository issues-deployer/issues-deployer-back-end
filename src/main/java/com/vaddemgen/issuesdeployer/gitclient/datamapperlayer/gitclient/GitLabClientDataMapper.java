package com.vaddemgen.issuesdeployer.gitclient.datamapperlayer.gitclient;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.gitclient.GitClientDataMapper;
import com.vaddemgen.issuesdeployer.gitclient.businesslayer.GitLabAccount;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class GitLabClientDataMapper implements GitClientDataMapper<GitLabAccount> {

  @Override
  public Stream<SuperGroup> findSuperGroups(GitLabAccount gitAccount) {
    return null;
  }

  @Override
  public Stream<SubGroup> findSubGroups(GitLabAccount gitAccount) {
    return null;
  }

  @Override
  public Stream<Project> findProjects(GitLabAccount gitAccount) {
    return null;
  }

  @Override
  public Stream<Issue> findIssues(GitLabAccount gitAccount) {
    return null;
  }
}
