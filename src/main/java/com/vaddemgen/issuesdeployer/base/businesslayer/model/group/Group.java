package com.vaddemgen.issuesdeployer.base.businesslayer.model.group;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.DomainModel;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString(doNotUseGetters = true)
public abstract class Group implements DomainModel {

  private static final long serialVersionUID = 6808378032323148790L;

  final long id;

  final long remoteId;

  @NonNull
  final String code;

  @NonNull
  final String path;

  @NonNull
  final String shortName;

  @NonNull
  final String name;

  @NonNull
  final URL webUrl;

  final String description;

  @NonNull
  final List<Project> projects;

  Group(long id, long remoteId, String code, String path, String shortName, String name,
      URL webUrl, String description, List<Project> projects) {
    this.id = id;
    this.remoteId = remoteId;
    this.code = code;
    this.path = path;
    this.shortName = shortName;
    this.name = name;
    this.webUrl = webUrl;
    this.description = description;
    this.projects = nonNull(projects) ? List.copyOf(projects) : emptyList();
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
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
        && path.equals(group.path)
        && shortName.equals(group.shortName)
        && name.equals(group.name)
        && webUrl.equals(group.webUrl)
        && Objects.equals(description, group.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, remoteId);
  }

  protected static abstract class GroupBuilder {

    Long id;

    Long remoteId;

    String code;

    String path;

    String shortName;

    String name;

    URL webUrl;

    String description;

    List<Project> projects = emptyList();

    public GroupBuilder id(long id) {
      this.id = id;
      return this;
    }

    public GroupBuilder remoteId(long remoteId) {
      this.remoteId = remoteId;
      return this;
    }

    public GroupBuilder code(@NonNull String code) {
      this.code = code;
      return this;
    }

    public GroupBuilder path(@NonNull String path) {
      this.path = path;
      return this;
    }

    public GroupBuilder shortName(@NonNull String shortName) {
      this.shortName = shortName;
      return this;
    }

    public GroupBuilder name(@NonNull String name) {
      this.name = name;
      return this;
    }

    public GroupBuilder webUrl(@NonNull URL webUrl) {
      this.webUrl = webUrl;
      return this;
    }

    public GroupBuilder description(String description) {
      this.description = description;
      return this;
    }

    public GroupBuilder projects(@NonNull List<Project> projects) {
      this.projects = List.copyOf(projects);
      return this;
    }

    public abstract Group build();
  }
}
