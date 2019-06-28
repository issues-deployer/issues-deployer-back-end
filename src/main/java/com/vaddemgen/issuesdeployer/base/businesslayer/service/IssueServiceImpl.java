package com.vaddemgen.issuesdeployer.base.businesslayer.service;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.IssueDataMapper;
import java.util.List;
import java.util.stream.Stream;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public final class IssueServiceImpl implements IssueService {

  private final IssueDataMapper issueDataMapper;

  public IssueServiceImpl(@NonNull IssueDataMapper issueDataMapper) {
    this.issueDataMapper = issueDataMapper;
  }

  @Override
  public Stream<Issue> mergeIssues(
      @NonNull Project issuesOwner,
      @NonNull List<Issue> issuesForMerge
  ) {
    return issueDataMapper.mergeIssues(issuesOwner, issuesForMerge);
  }
}
