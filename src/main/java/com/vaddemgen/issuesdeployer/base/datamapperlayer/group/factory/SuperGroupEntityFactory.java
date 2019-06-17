package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.gitaccount.GitAccountEntity;
import org.jetbrains.annotations.NotNull;

public final class SuperGroupEntityFactory {

  private SuperGroupEntityFactory() {
  }

  public static SuperGroupEntity createSuperGroupEntity(
      @NotNull SuperGroup group,
      @NotNull GitAccountEntity gitAccountEntity
  ) {
    return SuperGroupEntity.builder()
        .remoteId(group.getRemoteId())
        .code(group.getCode())
        .name(group.getName())
        .shortName(group.getShortName())
        .path(group.getPath())
        .webUrl(group.getWebUrl())
        .description(group.getDescription())
        .gitAccount(gitAccountEntity)
        .build();
  }
}
