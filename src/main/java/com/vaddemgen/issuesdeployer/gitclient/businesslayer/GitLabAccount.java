package com.vaddemgen.issuesdeployer.gitclient.businesslayer;

import static java.util.Objects.requireNonNull;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.AbstractGitAccount;
import java.util.Objects;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public final class GitLabAccount extends AbstractGitAccount {

  @NotNull
  private final String token;

  private GitLabAccount(@Nullable Long id, @NotNull User user, @NotNull String token) {
    super(id, user);
    this.token = token;
  }

  @NotNull
  public static GitLabAccountBuilder builder() {
    return new GitLabAccountBuilder();
  }

  @NotNull
  @Override
  public GitLabAccountBuilder clonePartially() {
    return builder()
        .id(getId())
        .user(getUser())
        .token(token);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    GitLabAccount that = (GitLabAccount) o;
    return token.equals(that.token);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), token);
  }

  public static final class GitLabAccountBuilder extends AbstractGitAccountBuilder {

    @Nullable
    private String token;

    GitLabAccountBuilder token(@NotNull String token) {
      this.token = token;
      return this;
    }

    @Override
    public GitLabAccountBuilder id(@Nullable Long id) {
      super.id(id);
      return this;
    }

    @Override
    public GitLabAccountBuilder user(@NotNull User user) {
      super.user(user);
      return this;
    }

    @Override
    public GitLabAccount build() {
      return new GitLabAccount(id, requireNonNull(user), requireNonNull(token));
    }
  }
}
