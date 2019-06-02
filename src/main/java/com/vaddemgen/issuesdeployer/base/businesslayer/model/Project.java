package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.List;
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

  private final long id;

  @NotNull
  private final Group group;

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
        .group(group)
        .code(code)
        .path(path)
        .name(name)
        .description(description)
        .createdAt(createdAt)
        .lastActivityAt(lastActivityAt)
        .webUrl(webUrl)
        .issues(issues);
  }
}
