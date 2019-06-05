package com.vaddemgen.issuesdeployer.test.util;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import org.jetbrains.annotations.NotNull;

public final class GitAccountTestUtils {

  /**
   * Don't let anyone to instantiate this class.
   */
  private GitAccountTestUtils() {
  }

  public static GitAccount createGitAccount(@NotNull User user) {
    return GitAccountStub.builder()
        .user(user)
        .build();
  }
}