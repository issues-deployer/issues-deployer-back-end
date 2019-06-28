package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString(doNotUseGetters = true)
public final class Project implements DomainModel {

  private static final long serialVersionUID = 1330099520409591266L;

  private final long id;

  private final long remoteId;

  @NotNull
  private final String code;

  @NotNull
  private final String path;

  @NotNull
  private final String name;

  @Nullable
  private final String description;

  @Nullable
  private final ZonedDateTime lastActivityAt;

  @NotNull
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
    return id == project.id &&
        remoteId == project.remoteId &&
        code.equals(project.code) &&
        path.equals(project.path) &&
        name.equals(project.name) &&
        Objects.equals(description, project.description) &&
        Objects.equals(lastActivityAt, project.lastActivityAt) &&
        webUrl.equals(project.webUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code);
  }
}
