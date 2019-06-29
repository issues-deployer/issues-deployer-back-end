package com.vaddemgen.issuesdeployer.base.businesslayer.model.group;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import java.net.URL;
import java.util.List;
import lombok.Getter;

@Getter
public final class SubGroup extends Group {

  private static final long serialVersionUID = -13083789785434619L;

  private SubGroup(long id, long remoteId, String code, String path, String shortName,
      String name, URL webUrl, String description, List<Project> projects) {
    super(id, remoteId, code, path, shortName, name, webUrl, description, projects);
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
        .projects(projects);
  }

  public static final class SubGroupBuilder extends GroupBuilder {

    @Override
    public SubGroupBuilder id(long id) {
      super.id(id);
      return this;
    }

    @Override
    public SubGroupBuilder remoteId(long remoteId) {
      super.remoteId(remoteId);
      return this;
    }

    @Override
    public SubGroupBuilder code(String code) {
      super.code(code);
      return this;
    }

    @Override
    public SubGroupBuilder path(String path) {
      super.path(path);
      return this;
    }

    @Override
    public SubGroupBuilder shortName(String shortName) {
      super.shortName(shortName);
      return this;
    }

    @Override
    public SubGroupBuilder name(String name) {
      super.name(name);
      return this;
    }

    @Override
    public SubGroupBuilder webUrl(URL webUrl) {
      super.webUrl(webUrl);
      return this;
    }

    @Override
    public SubGroupBuilder description(String description) {
      super.description(description);
      return this;
    }

    @Override
    public SubGroupBuilder projects(List<Project> projects) {
      super.projects(projects);
      return this;
    }

    @Override
    public SubGroup build() {
      return new SubGroup(id, remoteId, code, path, shortName, name, webUrl, description, projects);
    }
  }
}
