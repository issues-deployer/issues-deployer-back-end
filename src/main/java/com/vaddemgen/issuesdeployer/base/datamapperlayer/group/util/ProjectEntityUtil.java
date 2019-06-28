package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.util;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.ProjectEntity;

public final class ProjectEntityUtil {

  private ProjectEntityUtil() {
  }

  public static void mergeProjectEntity(Project from, ProjectEntity to) {
    to.setRemoteId(from.getRemoteId());
    to.setCode(from.getCode());
    to.setName(from.getName());
    to.setPath(from.getPath());
    to.setDescription(from.getDescription().orElse(null));

    if (from.getLastActivityAt().isEmpty()
        || to.getLastActivityAt().isEmpty()
        || !from.getLastActivityAt().get().isEqual(to.getLastActivityAt().get())
    ) {
      to.setLastActivityAt(from.getLastActivityAt().orElse(null));
    }

    to.setWebUrl(from.getWebUrl());
  }
}

