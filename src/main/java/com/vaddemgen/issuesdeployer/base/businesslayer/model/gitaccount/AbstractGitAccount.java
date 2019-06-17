package com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import java.util.Objects;
import java.util.Optional;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public abstract class AbstractGitAccount implements GitAccount {

  private static final long serialVersionUID = -3874608015069708289L;

  @Nullable
  private final Long id;

  @Nullable
  private final User user;

  public final Optional<Long> getId() {
    return Optional.ofNullable(id);
  }

  public AbstractGitAccount(@Nullable Long id, @Nullable User user) {
    this.id = id;
    this.user = user;
  }

  @NotNull
  @Override
  public abstract AbstractGitAccountBuilder clonePartially();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractGitAccount that = (AbstractGitAccount) o;
    return Objects.equals(id, that.id) && Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, user);
  }

  public static abstract class AbstractGitAccountBuilder implements GitAccountBuilder {

    @Nullable
    protected Long id;

    @Nullable
    protected User user;

    @Override
    public AbstractGitAccountBuilder id(@Nullable Long id) {
      this.id = id;
      return this;
    }

    @Override
    public AbstractGitAccountBuilder user(@Nullable User user) {
      this.user = user;
      return this;
    }

    @Override
    public abstract AbstractGitAccount build();
  }
}
