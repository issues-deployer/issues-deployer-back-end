package com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@ToString(doNotUseGetters = true)
public abstract class AbstractGitAccount implements GitAccount {

  private static final long serialVersionUID = -3874608015069708289L;

  private final long id;

  public AbstractGitAccount(long id) {
    this.id = id;
  }

  public final long getId() {
    return id;
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
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public static abstract class AbstractGitAccountBuilder implements GitAccountBuilder {

    protected long id;

    @Nullable
    protected User user;

    @Override
    public AbstractGitAccountBuilder id(long id) {
      this.id = id;
      return this;
    }

    @Override
    public abstract AbstractGitAccount build();
  }
}
