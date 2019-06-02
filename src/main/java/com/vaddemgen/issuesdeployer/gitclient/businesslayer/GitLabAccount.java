package com.vaddemgen.issuesdeployer.gitclient.businesslayer;

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

  public GitLabAccount(@NotNull String token, @Nullable User user) {
    super(user);
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
        .user(getUser())
        .token(token);
  }

  public static final class GitLabAccountBuilder extends AbstractGitAccountBuilder {

    @Nullable
    private String token;

    public GitLabAccountBuilder token(@NotNull String token) {
      this.token = token;
      return this;
    }

    @NotNull
    @Override
    public GitLabAccountBuilder user(@Nullable User user) {
      super.user(user);
      return this;
    }

    @NotNull
    @Override
    public GitLabAccount build() {
      return new GitLabAccount(Objects.requireNonNull(token), user);
    }
  }
}
