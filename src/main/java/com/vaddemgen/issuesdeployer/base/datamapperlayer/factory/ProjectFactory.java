package com.vaddemgen.issuesdeployer.base.datamapperlayer.factory;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.ProjectEntity;
import java.util.Collections;

public final class ProjectFactory {

  private ProjectFactory() {
  }

  public static Project createProject(ProjectEntity project) {
    return Project.builder()
        .id(project.getId())
        .remoteId(project.getRemoteId())
        .code(project.getCode())
        .path(project.getPath())
        .name(project.getName())
        .description(project.getDescription().orElse(null))
        .lastActivityAt(project.getLastActivityAt().orElse(null))
        .webUrl(project.getWebUrl())
        .issues(Collections.emptyList())
        .build();
  }
}
