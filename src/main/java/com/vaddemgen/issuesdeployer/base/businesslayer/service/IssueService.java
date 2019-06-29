package com.vaddemgen.issuesdeployer.base.businesslayer.service;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import java.util.List;
import java.util.stream.Stream;

public interface IssueService {

  Stream<Issue> getIssuesBy(User user, long projectId);

  Stream<Issue> mergeIssues(Project issuesOwner, List<Issue> issuesForMerge);
}
