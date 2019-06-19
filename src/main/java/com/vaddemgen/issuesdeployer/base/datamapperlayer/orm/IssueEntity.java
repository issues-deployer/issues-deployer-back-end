package com.vaddemgen.issuesdeployer.base.datamapperlayer.orm;

import java.net.URL;
import java.time.ZonedDateTime;
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
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Builder
public final class IssueEntity implements DbEntity {

  private static final long serialVersionUID = -3572339177177307264L;

  @Id
  @GeneratedValue
  @Column(updatable = false, insertable = false)
  private long id;

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
  private String[] labels;

  @Column
  @NotNull
  @NonNull
  private URL webUrl;

  @NotNull
  @NonNull
  private ZonedDateTime updatedAt;
}
