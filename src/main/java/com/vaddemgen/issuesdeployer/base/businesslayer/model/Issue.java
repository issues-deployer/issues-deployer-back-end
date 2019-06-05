package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Builder
@AllArgsConstructor
public final class Issue implements DomainModel {

  private final long id;

  @NotNull
  private final Project project;

  @NotNull
  private final String title;

  @Nullable
  private final String description;

  @NotNull
  private final String[] labels;

  @Nullable
  private final URL webUrl;

  public Stream<String> getLabels() {
    return Arrays.stream(labels);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public IssueBuilder clonePartially() {
    return builder()
        .id(id)
        .project(project)
        .title(title)
        .description(description)
        .labels(labels)
        .webUrl(webUrl);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Issue issue = (Issue) o;
    return id == issue.id &&
        project.equals(issue.project) &&
        title.equals(issue.title) &&
        Objects.equals(description, issue.description) &&
        Arrays.equals(labels, issue.labels) &&
        Objects.equals(webUrl, issue.webUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, project, title);
  }
}
