package com.vaddemgen.issuesdeployer.base.datamapperlayer.factory;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.gitaccount.GitAccountEntity;
import com.vaddemgen.issuesdeployer.gitlabclient.businesslayer.GitLabAccount;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.orm.gitaccount.GitLabAccountEntity;
import org.jetbrains.annotations.NotNull;

public final class GitAccountFactory {

  private GitAccountFactory() {
  }

  public static GitAccount createGitAccount(@NotNull GitAccountEntity entity) {
    if (entity instanceof GitLabAccountEntity) {
      return GitLabAccount.builder()
          .id(entity.getId())
          .token(((GitLabAccountEntity) entity).getToken())
          .build();
    }
    throw new UnsupportedOperationException();
  }
}
