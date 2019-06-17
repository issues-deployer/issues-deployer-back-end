package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@AllArgsConstructor
@Builder
public final class Project implements DomainModel {

  private static final long serialVersionUID = 1330099520409591266L;

  private final long id;

  private final long remoteId;

  @NotNull
  private final String code;

  @Nullable
  private final String path;

  @NotNull
  private final String name;

  @Nullable
  private final String description;

  @Nullable
  private final ZonedDateTime createdAt;

  @Nullable
  private final ZonedDateTime lastActivityAt;

  @Nullable
  private final URL webUrl;

  @NotNull
  private final List<Issue> issues;

  public Stream<Issue> getIssues() {
    return issues.stream();
  }

  @Override
  public ProjectBuilder clonePartially() {
    return builder()
        .id(id)
        .remoteId(remoteId)
        .code(code)
        .path(path)
        .name(name)
        .description(description)
        .createdAt(createdAt)
        .lastActivityAt(lastActivityAt)
        .webUrl(webUrl)
        .issues(issues);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Project project = (Project) o;
    return id == project.id
        && code.equals(project.code)
        && Objects.equals(path, project.path)
        && name.equals(project.name)
        && Objects.equals(description, project.description)
        && Objects.equals(createdAt, project.createdAt)
        && Objects.equals(lastActivityAt, project.lastActivityAt)
        && Objects.equals(webUrl, project.webUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code);
  }
}
