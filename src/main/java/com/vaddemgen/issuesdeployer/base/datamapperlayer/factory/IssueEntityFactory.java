package com.vaddemgen.issuesdeployer.base.datamapperlayer.factory;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.IssueEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.ProjectEntity;

public final class IssueEntityFactory {

  private IssueEntityFactory() {
  }

  public static IssueEntity createIssueEntity(Issue entity, ProjectEntity projectEntity) {
    if (projectEntity == null) {
      throw new NullPointerException();
    }
    return IssueEntity.builder()
        .id(entity.getId())
        .remoteId(entity.getRemoteId())
        .code(entity.getCode())
        .title(entity.getTitle())
        .labels(entity.getLabels().toArray(String[]::new))
        .webUrl(entity.getWebUrl())
        .updatedAt(entity.getUpdatedAt())
        .project(projectEntity)
        .build();
  }
}
