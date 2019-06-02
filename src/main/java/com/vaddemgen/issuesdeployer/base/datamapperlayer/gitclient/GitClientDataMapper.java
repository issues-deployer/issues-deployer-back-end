package com.vaddemgen.issuesdeployer.base.datamapperlayer.gitclient;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.AbstractGitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.util.stream.Stream;

public interface GitClientDataMapper<T extends AbstractGitAccount> {

  Stream<SuperGroup> findSuperGroups(T gitAccount);

  Stream<SubGroup> findSubGroups(T gitAccount);

  Stream<Project> findProjects(T gitAccount);

  Stream<Issue> findIssues(T gitAccount);
}
