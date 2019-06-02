package com.vaddemgen.issuesdeployer.base.datamapperlayer.gitclient;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.AbstractGitAccount;
import org.jetbrains.annotations.NotNull;

public interface GitClientDataMapperFactory {

  @NotNull <T extends AbstractGitAccount> GitClientDataMapper<T> create(@NotNull T gitAccount);
}
