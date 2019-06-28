package com.vaddemgen.issuesdeployer.base.businesslayer.service;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import java.util.List;
import java.util.stream.Stream;

public interface IssueService {

  Stream<Issue> mergeIssues(Project issuesOwner, List<Issue> issuesForMerge);
}
