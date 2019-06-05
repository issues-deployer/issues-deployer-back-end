package com.vaddemgen.issuesdeployer.test.util;

import static java.util.Objects.requireNonNull;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.AbstractGitAccount;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class GitAccountStub extends AbstractGitAccount {

  private GitAccountStub(@Nullable Long id, @NotNull User user) {
    super(id, user);
  }

  static GitAccountStubBuilder builder() {
    return new GitAccountStubBuilder();
  }

  @Override
  public @NotNull GitAccountStubBuilder clonePartially() {
    return builder()
        .id(getId())
        .user(getUser());
  }

  public static final class GitAccountStubBuilder extends AbstractGitAccountBuilder {

    private GitAccountStubBuilder() {
    }

    @Override
    public GitAccountStubBuilder id(@Nullable Long id) {
      super.id(id);
      return this;
    }

    @Override
    public GitAccountStubBuilder user(@NotNull User user) {
      super.user(user);
      return this;
    }

    @Override
    public AbstractGitAccount build() {
      return new GitAccountStub(id, requireNonNull(user));
    }
  }
}
