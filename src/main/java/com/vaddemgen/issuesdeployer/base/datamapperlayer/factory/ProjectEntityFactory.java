package com.vaddemgen.issuesdeployer.base.datamapperlayer.factory;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.GroupEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.ProjectEntity;
import org.jetbrains.annotations.NotNull;

public final class ProjectEntityFactory {

  private ProjectEntityFactory() {
  }

  public static ProjectEntity createProjectEntity(Project project,
      @NotNull GroupEntity groupEntity) {
    return ProjectEntity.builder()
        .remoteId(project.getRemoteId())
        .code(project.getCode())
        .path(project.getPath())
        .name(project.getName())
        .description(project.getDescription().orElse(null))
        .lastActivityAt(project.getLastActivityAt().orElse(null))
        .webUrl(project.getWebUrl())
        .group(groupEntity)
        .build();
  }
}
