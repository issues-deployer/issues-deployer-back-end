package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.factory;

import static java.util.Collections.emptyList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.GitLabGroupDto;
import org.jetbrains.annotations.NotNull;

public final class SubGroupFactory {

  // Don't let anyone to instantiate this class.
  private SubGroupFactory() {
  }

  public static SubGroup createSubGroup(@NotNull GitLabGroupDto dto) {
    return SubGroup.builder()
        .id(dto.getId())
        .code(dto.getPath())
        .path(dto.getPath())
        .shortName(dto.getName())
        .name(dto.getFullName())
        .webUrl(dto.getWebUrl())
        .description(dto.getDescription().orElse(null))
        .projects(emptyList())
        .build();
  }
}
