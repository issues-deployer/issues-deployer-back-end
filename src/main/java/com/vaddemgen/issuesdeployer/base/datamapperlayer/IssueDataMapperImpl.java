package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public final class IssueDataMapperImpl implements IssueDataMapper {

  @Override
  public Stream<Issue> findIssuesByProject(@NotNull Project project) {
    return Stream.empty();
  }
}
