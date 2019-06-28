package com.vaddemgen.issuesdeployer.base.datamapperlayer.orm;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity(name = "Issue")
@Table(
    name = "issue",
    uniqueConstraints = @UniqueConstraint(columnNames = {"remoteId", "project_id"})
)
@Getter
@Setter
@NoArgsConstructor
public final class IssueEntity implements DbEntity {

  private static final long serialVersionUID = -3572339177177307264L;

  @Id
  @GeneratedValue
  @Column(updatable = false, insertable = false)
  private long id;

  @NotNull
  @ManyToOne
  private ProjectEntity project;

  @Column
  @NotNull
  private long remoteId;

  @Column(length = 64)
  @NotNull
  @NotBlank
  @Size(min = 1, max = 64)
  @NonNull
  private String code;

  @Column
  @NotNull
  @NotBlank
  @Size(min = 1, max = 255)
  @NonNull
  private String title;

  @Column
  @NotNull
  @NonNull
  private String labels;

  @Column
  @NotNull
  @NonNull
  private URL webUrl;

  private ZonedDateTime updatedAt;

  @Builder
  public IssueEntity(
      long remoteId,
      @NonNull ProjectEntity project,
      @NonNull String code,
      @NonNull String title,
      @NonNull String[] labels,
      @NonNull URL webUrl,
      @NonNull ZonedDateTime updatedAt
  ) {
    this.project = project;
    this.remoteId = remoteId;
    this.code = code;
    this.title = title;
    setLabels(labels);
    this.webUrl = webUrl;
    this.updatedAt = updatedAt;
  }

  public Optional<ZonedDateTime> getUpdatedAt() {
    return Optional.ofNullable(updatedAt);
  }

  public Stream<String> getLabels() {
    return Stream.of(labels.split(","));
  }

  public void setLabels(@NonNull String[] labels) {
    this.labels = String.join(",", labels);
  }
}
