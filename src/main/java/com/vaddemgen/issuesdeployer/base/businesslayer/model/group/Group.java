package com.vaddemgen.issuesdeployer.base.businesslayer.model.group;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.DomainModel;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public abstract class Group implements DomainModel {

  final long id;

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

  Group(long id, @NotNull String code, @Nullable String path, @NotNull String shortName,
      @Nullable String name, @Nullable URL webUrl, @Nullable String description,
      @NotNull List<Project> projects) {
    this.id = id;
    this.code = code;
    this.path = path;
    this.shortName = shortName;
    this.name = name;
    this.webUrl = webUrl;
    this.description = description;
    this.projects = Collections.unmodifiableList(projects);
  }

  @Override
  public abstract GroupBuilder clonePartially();

  public Stream<Project> getProjects() {
    return projects.stream();
  }

  protected static abstract class GroupBuilder {

    long id;

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
      this.projects = Collections.unmodifiableList(projects);
      return this;
    }

    public abstract Group build();
  }
}
