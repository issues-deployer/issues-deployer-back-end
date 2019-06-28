package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.util;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.IssueEntity;

public final class IssueEntityUtil {

  private IssueEntityUtil() {
  }

  public static void mergeIssueEntity(Issue from, IssueEntity to) {
    to.setRemoteId(from.getRemoteId());
    to.setCode(from.getCode());
    to.setCode(from.getCode());
    to.setTitle(from.getTitle());
    to.setLabels(from.getLabels().toArray(String[]::new));

    if (from.getUpdatedAt().isEmpty()
        || to.getUpdatedAt().isEmpty()
        || !from.getUpdatedAt().get().isEqual(to.getUpdatedAt().get())
    ) {
      to.setUpdatedAt(from.getUpdatedAt().orElse(null));
    }

    to.setWebUrl(from.getWebUrl());
  }
}

