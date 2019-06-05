package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public interface IssueDataMapper {

  Stream<Issue> findIssuesByProject(@NotNull Project project);
}
