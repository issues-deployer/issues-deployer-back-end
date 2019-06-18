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
        .name(entity.getName().orElse(null))
        .shortName(entity.getShortName())
        .path(entity.getPath().orElse(null))
        .webUrl(entity.getWebUrl().orElse(null))
        .description(entity.getDescription().orElse(null))
        .projects(Collections.emptyList())
        .build();
  }
}
