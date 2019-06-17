package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SubGroupEntity;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;

public final class SubGroupFactory {

  private SubGroupFactory() {
  }

  public static SubGroup createSubGroup(@NotNull SubGroupEntity entity) {
    return SubGroup.builder()
        .id(entity.getId())
        .remoteId(entity.getRemoteId())
        .code(entity.getCode())
        .name(entity.getName())
        .shortName(entity.getShortName())
        .path(entity.getPath())
        .webUrl(entity.getWebUrl())
        .description(entity.getDescription())
        .projects(Collections.emptyList())
        .build();
  }
}
