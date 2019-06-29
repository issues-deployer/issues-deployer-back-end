package com.vaddemgen.issuesdeployer.base.businesslayer.model.group;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString(callSuper = true, doNotUseGetters = true)
public final class SuperGroup extends Group {

  private static final long serialVersionUID = -5605482196809175130L;

  @NonNull
  private final List<SubGroup> subGroups;

  private SuperGroup(long id, long remoteId, String code, String path, String shortName,
      String name, URL webUrl, String description, List<Project> projects,
      List<SubGroup> subGroups) {
    super(id, remoteId, code, path, shortName, name, webUrl, description, projects);
    this.subGroups = nonNull(subGroups) ? List.copyOf(subGroups) : emptyList();
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
        .subGroups(subGroups);
  }

  public static final class SuperGroupBuilder extends GroupBuilder {


    private List<SubGroup> subGroups = emptyList();

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

    public SuperGroupBuilder subGroups(@NonNull List<SubGroup> subGroups) {
      this.subGroups = Collections.unmodifiableList(subGroups);
      return this;
    }

    @Override
    public SuperGroupBuilder code(String code) {
      super.code(code);
      return this;
    }

    @Override
    public SuperGroupBuilder path(String path) {
      super.path(path);
      return this;
    }

    @Override
    public SuperGroupBuilder shortName(String shortName) {
      super.shortName(shortName);
      return this;
    }

    @Override
    public SuperGroupBuilder name(String name) {
      super.name(name);
      return this;
    }

    @Override
    public SuperGroupBuilder webUrl(URL webUrl) {
      super.webUrl(webUrl);
      return this;
    }

    @Override
    public SuperGroupBuilder description(String description) {
      super.description(description);
      return this;
    }

    @Override
    public SuperGroupBuilder projects(List<Project> projects) {
      super.projects(projects);
      return this;
    }

    @Override
    public SuperGroup build() {
      return new SuperGroup(id, remoteId, code, path, shortName, name, webUrl, description,
          projects, subGroups);
    }
  }
}
