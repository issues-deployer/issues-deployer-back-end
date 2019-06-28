package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.factory;

import static java.util.Collections.emptyList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.GitLabProjectDto;
import java.time.ZonedDateTime;
import org.jetbrains.annotations.NotNull;

public final class ProjectFactory {

  // Don't let anyone to instantiate this class.
  private ProjectFactory() {
  }

  public static Project createProject(@NotNull GitLabProjectDto dto) {
    return Project.builder()
        .id(-1)
        .remoteId(dto.getRemoteId())
        .name(dto.getName())
        .code(dto.getPath())
        .path(dto.getPath())
        .webUrl(dto.getWebUrl())
        .description(dto.getDescription().orElse(null))
        .lastActivityAt(ZonedDateTime.parse(dto.getLastActivityAt()))
        .issues(emptyList())
        .build();
  }
}
