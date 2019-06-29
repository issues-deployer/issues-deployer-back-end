package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import java.util.List;
import java.util.stream.Stream;

public interface IssueDataMapper {

  Stream<Issue> mergeIssues(Project issuesOwner, List<Issue> issuesForMerge);

  Stream<Issue> findIssuesBy(User user, long projectId);
}
