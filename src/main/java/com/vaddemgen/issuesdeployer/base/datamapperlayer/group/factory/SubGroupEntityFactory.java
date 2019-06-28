package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory;

import static java.util.Collections.emptySet;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SubGroupEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import org.jetbrains.annotations.NotNull;

public final class SubGroupEntityFactory {

  private SubGroupEntityFactory() {
  }

  public static SubGroupEntity createSubGroupEntity(
      @NotNull SubGroup group,
      @NotNull SuperGroupEntity superGroupEntity
  ) {
    return SubGroupEntity.builder()
        .remoteId(group.getRemoteId())
        .superGroup(superGroupEntity)
        .code(group.getCode())
        .name(group.getName())
        .shortName(group.getShortName())
        .path(group.getPath())
        .webUrl(group.getWebUrl())
        .description(group.getDescription())
        .projects(emptySet())
        .build();
  }
}
