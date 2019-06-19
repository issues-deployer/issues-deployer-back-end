package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group;

import static java.util.Collections.emptySet;
import static java.util.Objects.nonNull;
import static javax.persistence.CascadeType.REMOVE;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.ProjectEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.gitaccount.GitAccountEntity;
import java.net.URL;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Setter
@NoArgsConstructor
@Entity(name = "SuperGroup")
@DiscriminatorValue(SuperGroupEntity.TYPE_SUPER_GROUP)
public final class SuperGroupEntity extends GroupEntity {

  static final String TYPE_SUPER_GROUP = "SUPER_GROUP";

  private static final long serialVersionUID = -7739796240091110001L;

  @Nullable
  @OneToMany(mappedBy = "superGroup", cascade = REMOVE)
  private Set<SubGroupEntity> subGroups = emptySet();

  @Builder
  public SuperGroupEntity(
      long remoteId,
      @NotNull GitAccountEntity gitAccount,
      @NotNull String code,
      @NotNull String shortName,
      @NotNull String name,
      @NotNull String path,
      @NotNull Set<ProjectEntity> projects,
      @NotNull Set<SubGroupEntity> subGroups,
      URL webUrl,
      String description
  ) {
    super(remoteId, gitAccount, code, shortName, name, path, projects, webUrl, description);
    this.subGroups = Set.copyOf(subGroups);
  }

  public Stream<SubGroupEntity> getSubGroups() {
    return nonNull(subGroups) ? subGroups.stream() : Stream.empty();
  }
}
