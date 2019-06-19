package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group;

import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SubGroupEntity.TYPE_SUB_GROUP;
import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity.TYPE_SUPER_GROUP;
import static java.util.Objects.nonNull;
import static javax.persistence.CascadeType.REMOVE;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.DbEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.ProjectEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.gitaccount.GitAccountEntity;
import java.net.URL;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorFormula;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Group")
@Table(
    name = "\"group\"",
    uniqueConstraints = @UniqueConstraint(columnNames = {"remoteId", "git_account_id"})
)
@DiscriminatorFormula(
    "CASE "
        + "WHEN parent_id IS NULL THEN '" + TYPE_SUPER_GROUP + "' "
        + "WHEN parent_id IS NOT NULL THEN '" + TYPE_SUB_GROUP + "' "
        + "END"
)
@Inheritance
public abstract class GroupEntity implements DbEntity {

  static final String COLUMN_TYPE = "type";

  private static final long serialVersionUID = -4820770217526656142L;

  @Id
  @GeneratedValue
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column
  @NotNull
  private Long remoteId;

  @ManyToOne
  @NotNull
  private GitAccountEntity gitAccount;

  @Column(length = 64)
  @NotNull
  @NotBlank
  @Size(min = 1, max = 64)
  private String code;

  @Column(length = 128)
  @NotNull
  @NotBlank
  @Size(min = 1, max = 128)
  private String shortName;

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

  @Column
  private URL webUrl;

  @Column(length = 2048)
  @Size(max = 2048)
  private String description;

  @Nullable
  @OneToMany(mappedBy = "group", cascade = REMOVE)
  private Set<ProjectEntity> projects;

  GroupEntity(
      long remoteId,
      @NotNull GitAccountEntity gitAccount,
      @NotNull String code,
      @NotNull String shortName,
      @NotNull String name,
      @NotNull String path,
      @NotNull Set<ProjectEntity> projects,
      URL webUrl,
      String description
  ) {
    this.remoteId = remoteId;
    this.gitAccount = gitAccount;
    this.code = code;
    this.shortName = shortName;
    this.name = name;
    this.path = path;
    this.projects = Set.copyOf(projects);
    this.webUrl = webUrl;
    this.description = description;
  }

  public Optional<String> getName() {
    return Optional.ofNullable(name);
  }

  public Optional<String> getPath() {
    return Optional.ofNullable(path);
  }

  public Optional<URL> getWebUrl() {
    return Optional.ofNullable(webUrl);
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public Stream<ProjectEntity> getProjects() {
    return nonNull(projects) ? projects.stream() : Stream.empty();
  }
}
