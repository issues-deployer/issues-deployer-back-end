package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import java.net.URL;
import java.util.Arrays;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Builder
@AllArgsConstructor
public final class Issue implements DomainModel {

  private final long id;

  private final Project project;

  private final String title;

  private final String description;

  private final String[] labels;

  private final URL webUrl;

  public Stream<String> getLabels() {
    return Arrays.stream(labels);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
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
}
