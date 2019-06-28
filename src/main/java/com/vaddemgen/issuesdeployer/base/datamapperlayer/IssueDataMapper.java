package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public interface IssueDataMapper {

  Stream<Issue> findIssuesByProject(GitAccount gitAccount, Project project);

  Stream<Issue> saveIssues(Project project, Collection<Issue> issues);

  Stream<Issue> mergeIssues(Project issuesOwner, List<Issue> issuesForMerge);
}
