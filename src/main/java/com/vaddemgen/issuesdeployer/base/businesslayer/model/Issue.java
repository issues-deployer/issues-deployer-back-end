package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.Nullable;

@Getter
public final class Issue implements DomainModel {

  private static final long serialVersionUID = -5190031094697232656L;

  private final long id;

  private final long remoteId;

  @NonNull
  private final String code;

  @NonNull
  private final String title;

  @NonNull
  private final String[] labels;

  @NonNull
  private final URL webUrl;

  @Nullable
  private final ZonedDateTime updatedAt;

  @Builder
  public Issue(long id, long remoteId, String code, String title, String[] labels, URL webUrl,
      @Nullable ZonedDateTime updatedAt) {
    this.id = id;
    this.remoteId = remoteId;
    this.code = code;
    this.title = title;
    this.labels = Objects.nonNull(labels) ? Arrays.copyOf(labels, labels.length) : new String[0];
    this.webUrl = webUrl;
    this.updatedAt = updatedAt;
  }

  public Stream<String> getLabels() {
    return Arrays.stream(labels);
  }

  public Optional<ZonedDateTime> getUpdatedAt() {
    return Optional.ofNullable(updatedAt);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public IssueBuilder clonePartially() {
    return builder()
        .id(id)
        .remoteId(remoteId)
        .code(code)
        .title(title)
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
        remoteId == issue.remoteId &&
        code.equals(issue.code) &&
        title.equals(issue.title) &&
        Arrays.equals(labels, issue.labels) &&
        webUrl.equals(issue.webUrl) &&
        Objects.equals(updatedAt, issue.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, remoteId, code);
  }
}
