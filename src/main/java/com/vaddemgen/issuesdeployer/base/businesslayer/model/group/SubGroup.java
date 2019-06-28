package com.vaddemgen.issuesdeployer.base.businesslayer.model.group;

import static java.util.Objects.requireNonNull;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public final class SubGroup extends Group {

  private static final long serialVersionUID = -13083789785434619L;

  @Nullable
  private final SuperGroup superGroup;

  private SubGroup(
      long id,
      long remoteId,
      @NotNull String code,
      @NotNull String shortName,
      @NotNull List<Project> projects,
      @Nullable String path,
      @Nullable String name,
      @Nullable URL webUrl,
      @Nullable String description,
      @Nullable SuperGroup superGroup
  ) {
    super(id, remoteId, code, shortName, projects, path, name, webUrl, description);
    this.superGroup = superGroup;
  }

  public static SubGroupBuilder builder() {
    return new SubGroupBuilder();
  }

  @Override
  public SubGroupBuilder clonePartially() {
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
        .superGroup(superGroup);
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
    SubGroup subGroup = (SubGroup) o;
    return Objects.equals(superGroup, subGroup.superGroup);
  }

  public static final class SubGroupBuilder extends GroupBuilder {

    @Nullable
    private SuperGroup superGroup;

    @Override
    public SubGroupBuilder code(@NotNull String code) {
      super.code(code);
      return this;
    }

    @Override
    public SubGroupBuilder remoteId(long remoteId) {
      super.remoteId(remoteId);
      return this;
    }

    @Override
    public SubGroupBuilder path(@Nullable String path) {
      super.path(path);
      return this;
    }

    @Override
    public SubGroupBuilder shortName(@NotNull String shortName) {
      super.shortName(shortName);
      return this;
    }

    @Override
    public SubGroupBuilder name(@Nullable String name) {
      super.name(name);
      return this;
    }

    @Override
    public SubGroupBuilder webUrl(@Nullable URL webUrl) {
      super.webUrl(webUrl);
      return this;
    }

    @Override
    public SubGroupBuilder description(@Nullable String description) {
      super.description(description);
      return this;
    }

    @Override
    public SubGroupBuilder projects(@NotNull List<Project> projects) {
      super.projects(projects);
      return this;
    }

    public SubGroupBuilder superGroup(@Nullable SuperGroup superGroup) {
      this.superGroup = superGroup;
      return this;
    }

    @Override
    public SubGroupBuilder id(long id) {
      super.id(id);
      return this;
    }

    @Override
    public SubGroup build() {
      return new SubGroup(requireNonNull(id), requireNonNull(remoteId), requireNonNull(code),
          requireNonNull(shortName),
          projects, path, name, webUrl, description, superGroup);
    }
  }
}
