package com.vaddemgen.issuesdeployer.base.businesslayer.model.group;

import static java.util.Objects.requireNonNull;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public final class SuperGroup extends Group {

  @NotNull
  private final GitAccount gitAccount;

  @NotNull
  private final List<SubGroup> subGroups;

  public SuperGroup(long id, @NotNull String code, @Nullable String path, @NotNull String shortName,
      @Nullable String name, @Nullable URL webUrl, @Nullable String description,
      @NotNull List<Project> projects, @NotNull GitAccount gitAccount,
      @NotNull List<SubGroup> subGroups) {
    super(id, code, path, shortName, name, webUrl, description, projects);
    this.gitAccount = gitAccount;
    this.subGroups = Collections.unmodifiableList(subGroups);
  }

  public static SuperGroupBuilder builder() {
    return new SuperGroupBuilder();
  }

  public Stream<SubGroup> getSubGroups() {
    return subGroups.stream();
  }

  @Override
  public SuperGroupBuilder clonePartially() {
    return builder()
        .id(id)
        .code(code)
        .path(path)
        .shortName(shortName)
        .name(name)
        .webUrl(webUrl)
        .description(description)
        .projects(projects)
        .gitAccount(gitAccount)
        .subGroups(subGroups);
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
    SuperGroup that = (SuperGroup) o;
    return gitAccount.equals(that.gitAccount);
  }

  public static final class SuperGroupBuilder extends GroupBuilder {

    @Nullable
    private GitAccount gitAccount;

    private List<SubGroup> subGroups = Collections.emptyList();

    @Override
    public SuperGroupBuilder id(long id) {
      super.id(id);
      return this;
    }

    public SuperGroupBuilder gitAccount(@NotNull GitAccount gitAccount) {
      this.gitAccount = gitAccount;
      return this;
    }

    public SuperGroupBuilder subGroups(@NotNull List<SubGroup> subGroups) {
      this.subGroups = Collections.unmodifiableList(subGroups);
      return this;
    }

    @Override
    public SuperGroupBuilder code(@NotNull String code) {
      super.code(code);
      return this;
    }

    @Override
    public SuperGroupBuilder path(@Nullable String path) {
      super.path(path);
      return this;
    }

    @Override
    public SuperGroupBuilder shortName(@NotNull String shortName) {
      super.shortName(shortName);
      return this;
    }

    @Override
    public SuperGroupBuilder name(@Nullable String name) {
      super.name(name);
      return this;
    }

    @Override
    public SuperGroupBuilder webUrl(@Nullable URL webUrl) {
      super.webUrl(webUrl);
      return this;
    }

    @Override
    public SuperGroupBuilder description(@Nullable String description) {
      super.description(description);
      return this;
    }

    @Override
    public SuperGroupBuilder projects(@NotNull List<Project> projects) {
      super.projects(projects);
      return this;
    }

    @Override
    public SuperGroup build() {
      return new SuperGroup(id, requireNonNull(code), path, requireNonNull(shortName), name, webUrl,
          description, projects, requireNonNull(gitAccount), subGroups);
    }
  }
}
