package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;

public final class SuperGroupFactory {

  private SuperGroupFactory() {
  }

  public static SuperGroup createSuperGroup(@NotNull SuperGroupEntity entity) {
    return SuperGroup.builder()
        .id(entity.getId())
        .remoteId(entity.getRemoteId())
        .code(entity.getCode())
        .name(entity.getName().orElse(null))
        .shortName(entity.getShortName())
        .path(entity.getPath().orElse(null))
        .webUrl(entity.getWebUrl().orElse(null))
        .description(entity.getDescription().orElse(null))
        .subGroups(Collections.emptyList())
        .projects(Collections.emptyList())
        .build();
  }
}
