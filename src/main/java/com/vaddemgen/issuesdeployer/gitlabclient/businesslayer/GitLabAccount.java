package com.vaddemgen.issuesdeployer.gitlabclient.businesslayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.AbstractGitAccount;
import java.util.Objects;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString(callSuper = true, doNotUseGetters = true)
public final class GitLabAccount extends AbstractGitAccount {

  private static final long serialVersionUID = 6552417711945483815L;

  private final String token;

  private GitLabAccount(long id, @NonNull String token) {
    super(id);
    this.token = token;
  }

  public static GitLabAccountBuilder builder() {
    return new GitLabAccountBuilder();
  }

  @Override
  public GitLabAccountBuilder clonePartially() {
    return builder()
        .id(getId())
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

    private String token;

    @Override
    public GitLabAccountBuilder id(long id) {
      super.id(id);
      return this;
    }

    public GitLabAccountBuilder token(@NonNull String token) {
      this.token = token;
      return this;
    }

    @Override
    public GitLabAccount build() {
      return new GitLabAccount(id, token);
    }
  }
}
