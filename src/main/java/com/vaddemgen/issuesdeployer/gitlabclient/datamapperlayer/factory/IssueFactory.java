package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.factory;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.GitLabIssueDto;
import java.time.ZonedDateTime;
import org.jetbrains.annotations.NotNull;

public final class IssueFactory {

  // Don't let anyone to instantiate this class.
  private IssueFactory() {
  }

  public static Issue createIssue(@NotNull GitLabIssueDto dto) {
    return Issue.builder()
        .remoteId(dto.getRemoteId())
        .code(dto.getCode())
        .title(dto.getTitle())
        .labels(dto.getLabels().toArray(String[]::new))
        .webUrl(dto.getWebUrl())
        .updatedAt(ZonedDateTime.parse(dto.getUpdatedAt()))
        .build();
  }
}
