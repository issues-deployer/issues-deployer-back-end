package com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.gitlabclient.businesslayer.GitLabAccount;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.GitLabClient;
import org.jetbrains.annotations.NotNull;

public final class GitClientFactory {

  // Don't let anyone to instantiate this class.
  private GitClientFactory() {
  }

  public static <T extends GitAccount> GitClient<T> createGitClient(@NotNull T gitAccount) {
    if (gitAccount instanceof GitLabAccount) {
      //noinspection unchecked
      return (GitClient<T>) new GitLabClient((GitLabAccount) gitAccount);
    }
    throw new UnsupportedOperationException("The git client '" + gitAccount.getClass()
        + "' is not supported.");
  }
}
