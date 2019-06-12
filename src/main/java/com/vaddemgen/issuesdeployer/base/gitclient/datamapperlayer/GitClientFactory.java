package com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.gitlabclient.businesslayer.GitLabAccount;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.GitLabCacheManager;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.GitLabClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public final class GitClientFactory {

  private final GitLabCacheManager cacheManager;

  public GitClientFactory(@NotNull GitLabCacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  public <T extends GitAccount> GitClient<T> createGitClient(@NotNull T gitAccount) {
    if (gitAccount instanceof GitLabAccount) {
      //noinspection unchecked
      return (GitClient<T>) new GitLabClient((GitLabAccount) gitAccount, cacheManager);
    }
    throw new UnsupportedOperationException("The git client '" + gitAccount.getClass()
        + "' is not supported.");
  }
}
