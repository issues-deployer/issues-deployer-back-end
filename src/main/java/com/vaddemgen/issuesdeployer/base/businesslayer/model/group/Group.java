package com.vaddemgen.issuesdeployer.base.businesslayer.model.group;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.DomainModel;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
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
@ToString(doNotUseGetters = true)
public abstract class Group implements DomainModel {

  private static final long serialVersionUID = 6808378032323148790L;

  final long id;

  final long remoteId;

  @NotNull
  final String code;

  @Nullable
  final String path;

  @NotNull
  final String shortName;

  @Nullable
  final String name;

  @Nullable
  final URL webUrl;

  @Nullable
  final String description;

  @NotNull
  final List<Project> projects;

  Group(
      long id,
      long remoteId,
      @NotNull String code,
      @NotNull String shortName,
      @NotNull List<Project> projects,
      @Nullable String path,
      @Nullable String name,
      @Nullable URL webUrl,
      @Nullable String description
  ) {
    this.id = id;
    this.remoteId = remoteId;
    this.code = code;
    this.shortName = shortName;
    this.projects = List.copyOf(projects);
    this.path = path;
    this.name = name;
    this.webUrl = webUrl;
    this.description = description;
  }

  @Override
  public abstract GroupBuilder clonePartially();

  public Stream<Project> getProjects() {
    return projects.stream();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Group group = (Group) o;
    return id == group.id
        && remoteId == group.remoteId
        && code.equals(group.code)
        && Objects.equals(path, group.path)
        && shortName.equals(group.shortName)
        && Objects.equals(name, group.name)
        && Objects.equals(webUrl, group.webUrl)
        && Objects.equals(description, group.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, remoteId);
  }

  protected static abstract class GroupBuilder {

    @Nullable
    Long id;

    @Nullable
    Long remoteId;

    @Nullable
    String code;

    @Nullable
    String path;

    @Nullable
    String shortName;

    @Nullable
    String name;

    @Nullable
    URL webUrl;

    @Nullable
    String description;

    List<Project> projects = Collections.emptyList();

    public GroupBuilder id(long id) {
      this.id = id;
      return this;
    }

    public GroupBuilder remoteId(long remoteId) {
      this.remoteId = remoteId;
      return this;
    }

    public GroupBuilder code(@NotNull String code) {
      this.code = code;
      return this;
    }

    public GroupBuilder path(@Nullable String path) {
      this.path = path;
      return this;
    }

    public GroupBuilder shortName(@NotNull String shortName) {
      this.shortName = shortName;
      return this;
    }

    public GroupBuilder name(@Nullable String name) {
      this.name = name;
      return this;
    }

    public GroupBuilder webUrl(@Nullable URL webUrl) {
      this.webUrl = webUrl;
      return this;
    }

    public GroupBuilder description(@Nullable String description) {
      this.description = description;
      return this;
    }

    public GroupBuilder projects(@NotNull List<Project> projects) {
      this.projects = List.copyOf(projects);
      return this;
    }

    public abstract Group build();
  }
}
