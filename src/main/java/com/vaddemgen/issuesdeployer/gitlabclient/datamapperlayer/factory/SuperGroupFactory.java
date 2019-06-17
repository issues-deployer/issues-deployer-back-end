package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.factory;

import static java.util.Collections.emptyList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.gitlabclient.businesslayer.GitLabAccount;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.GitLabGroupDto;
import org.jetbrains.annotations.NotNull;

public final class SuperGroupFactory {

  // Don't let anyone to instantiate this class.
  private SuperGroupFactory() {
  }

  public static SuperGroup createSuperGroup(@NotNull GitLabGroupDto dto,
      @NotNull GitLabAccount gitAccount) {
    return SuperGroup.builder()
        .remoteId(dto.getId())
        .code(dto.getPath())
        .path(dto.getPath())
        .shortName(dto.getName())
        .name(dto.getFullName())
        .webUrl(dto.getWebUrl())
        .description(dto.getDescription().orElse(null))
        .projects(emptyList())
        .subGroups(emptyList())
        .gitAccount(gitAccount)
        .build();
  }
}
