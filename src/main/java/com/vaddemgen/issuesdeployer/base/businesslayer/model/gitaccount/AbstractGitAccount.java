package com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public abstract class AbstractGitAccount implements GitAccount {

  @Nullable
  private final User user;

  public AbstractGitAccount(@Nullable User user) {
    this.user = user;
  }

  @NotNull
  @Override
  public abstract AbstractGitAccountBuilder clonePartially();

  public static abstract class AbstractGitAccountBuilder implements GitAccountBuilder {

    @Nullable
    protected User user;

    @NotNull
    @Override
    public AbstractGitAccountBuilder user(@Nullable User user) {
      this.user = user;
      return this;
    }

    @NotNull
    @Override
    public abstract AbstractGitAccount build();
  }
}
