package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(doNotUseGetters = true)
public final class Project implements DomainModel {

  private static final long serialVersionUID = 1330099520409591266L;

  private final long id;

  private final long remoteId;

  @NonNull
  private final String code;

  @NonNull
  private final String path;

  @NonNull
  private final String name;

  @NonNull
  private final URL webUrl;

  private final String description;

  private final ZonedDateTime lastActivityAt;

  @NonNull
  private final List<Issue> issues;

  @Builder
  public Project(long id, long remoteId, String code, String path, String name, URL webUrl,
      String description, ZonedDateTime lastActivityAt, List<Issue> issues) {
    this.id = id;
    this.remoteId = remoteId;
    this.code = code;
    this.path = path;
    this.name = name;
    this.webUrl = webUrl;
    this.description = description;
    this.lastActivityAt = lastActivityAt;
    this.issues = nonNull(issues) ? List.copyOf(issues) : emptyList();
  }

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
        .lastActivityAt(lastActivityAt)
        .webUrl(webUrl)
        .issues(issues);
  }

  public Optional<ZonedDateTime> getLastActivityAt() {
    return Optional.ofNullable(lastActivityAt);
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
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
        && remoteId == project.remoteId
        && code.equals(project.code)
        && path.equals(project.path)
        && name.equals(project.name)
        && Objects.equals(description, project.description)
        && Objects.equals(lastActivityAt, project.lastActivityAt)
        && webUrl.equals(project.webUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code);
  }
}
