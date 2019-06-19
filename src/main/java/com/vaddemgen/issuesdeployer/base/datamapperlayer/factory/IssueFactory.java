package com.vaddemgen.issuesdeployer.base.datamapperlayer.factory;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.IssueEntity;

public final class IssueFactory {

  private IssueFactory() {
  }

  public static Issue createIssue(IssueEntity entity) {
    return Issue.builder()
        .id(entity.getId())
        .remoteId(entity.getRemoteId())
        .code(entity.getCode())
        .title(entity.getTitle())
        .labels(entity.getLabels())
        .webUrl(entity.getWebUrl())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }
}
