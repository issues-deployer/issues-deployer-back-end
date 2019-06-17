package com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractGitClient<T extends GitAccount> implements GitClient<T> {

  protected final T gitAccount;

  public AbstractGitClient(@NotNull T gitAccount) {
    this.gitAccount = gitAccount;
  }
}
