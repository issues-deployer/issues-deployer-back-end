package com.vaddemgen.issuesdeployer.base.datamapperlayer.gitclient;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.AbstractGitAccount;
import com.vaddemgen.issuesdeployer.gitclient.businesslayer.GitLabAccount;
import com.vaddemgen.issuesdeployer.gitclient.datamapperlayer.gitclient.GitLabClientDataMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public final class GitClientDataMapperFactoryImpl implements GitClientDataMapperFactory {

  private final GitLabClientDataMapper gitLabClientDataMapper;

  public GitClientDataMapperFactoryImpl(@NotNull GitLabClientDataMapper gitLabClientDataMapper) {
    this.gitLabClientDataMapper = gitLabClientDataMapper;
  }

  @Override
  public @NotNull <T extends AbstractGitAccount> GitClientDataMapper<T> create(
      @NotNull T gitAccount) {

    if (gitAccount instanceof GitLabAccount) {
      //noinspection unchecked
      return (GitClientDataMapper<T>) gitLabClientDataMapper;
    }
    throw new RuntimeException(
        "The git account " + gitAccount.getClass().getSimpleName() + " is not supported.");
  }
}
