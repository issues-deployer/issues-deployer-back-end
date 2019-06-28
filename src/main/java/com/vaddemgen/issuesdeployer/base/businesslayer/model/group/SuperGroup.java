package com.vaddemgen.issuesdeployer.base.businesslayer.model.group;

import static java.util.Objects.requireNonNull;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@ToString(callSuper = true, doNotUseGetters = true)
public final class SuperGroup extends Group {

  private static final long serialVersionUID = -5605482196809175130L;

  @Nullable
  private final GitAccount gitAccount;

  @NotNull
  private final List<SubGroup> subGroups;

  private SuperGroup(
      long id,
      long remoteId,
      @NotNull String code,
      @NotNull String shortName,
      @NotNull List<Project> projects,
      @Nullable String path,
      @Nullable String name,
      @Nullable URL webUrl,
      @Nullable String description,
      @Nullable GitAccount gitAccount,
      @NotNull List<SubGroup> subGroups
  ) {
    super(id, remoteId, code, shortName, projects, path, name, webUrl, description);
    this.gitAccount = gitAccount;
    this.subGroups = List.copyOf(subGroups);
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
        .remoteId(remoteId)
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
    return Objects.equals(gitAccount, that.gitAccount);
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

    @Override
    public SuperGroupBuilder remoteId(long remoteId) {
      super.remoteId(remoteId);
      return this;
    }

    public SuperGroupBuilder gitAccount(@Nullable GitAccount gitAccount) {
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
      return new SuperGroup(requireNonNull(id), requireNonNull(remoteId), requireNonNull(code),
          requireNonNull(shortName), projects, path, name, webUrl, description, gitAccount,
          subGroups);
    }
  }
}
