package com.vaddemgen.issuesdeployer.base.datamapperlayer.orm;

import static java.util.Collections.emptySet;
import static java.util.Objects.nonNull;
import static javax.persistence.CascadeType.REMOVE;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.GroupEntity;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Project")
@Table(
    name = "project",
    uniqueConstraints = @UniqueConstraint(columnNames = {"remoteId", "group_id"})
)
public final class ProjectEntity implements DbEntity {

  private static final long serialVersionUID = -173492220954178385L;

  @Id
  @GeneratedValue
  @Column(updatable = false, insertable = false)
  private long id;

  @Column
  @NotNull
  private long remoteId;

  @ManyToOne
  @NotNull
  private GroupEntity group;

  @Column(length = 64)
  @NotNull
  @NotBlank
  @Size(min = 1, max = 64)
  private String code;

  @Column(length = 128)
  @NotNull
  @NotBlank
  @Size(min = 1, max = 128)
  private String name;

  @Column(length = 64)
  @NotNull
  @NotBlank
  @Size(min = 1, max = 64)
  private String path;

  @Column(length = 2048)
  @Size(max = 2048)
  private String description;

  @Column
  private ZonedDateTime lastActivityAt;

  @Column
  private URL webUrl;

  @NonNull
  @NotNull
  @OneToMany(mappedBy = "project", cascade = REMOVE)
  private Set<IssueEntity> issues;

  @Builder
  public ProjectEntity(
      long remoteId,
      @NonNull GroupEntity group,
      @NonNull String code,
      @NonNull String name,
      @NonNull String path,
      @NonNull URL webUrl,
      String description,
      ZonedDateTime lastActivityAt,
      Set<IssueEntity> issues
  ) {
    this.remoteId = remoteId;
    this.group = group;
    this.code = code;
    this.name = name;
    this.path = path;
    this.description = description;
    this.lastActivityAt = lastActivityAt;
    this.webUrl = webUrl;
    this.issues = nonNull(issues) ? Set.copyOf(issues) : emptySet();
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public Optional<ZonedDateTime> getLastActivityAt() {
    return Optional.ofNullable(lastActivityAt);
  }

  public Stream<IssueEntity> getIssues() {
    return nonNull(issues) ? issues.stream() : Stream.empty();
  }
}
