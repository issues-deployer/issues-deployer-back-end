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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "SuperGroup")
@DiscriminatorValue(SuperGroupEntity.TYPE_SUPER_GROUP)
public final class SuperGroupEntity extends GroupEntity {

  static final String TYPE_SUPER_GROUP = "SUPER_GROUP";

  private static final long serialVersionUID = -7739796240091110001L;

  @ManyToOne
  @NotNull
  @NonNull
  private GitAccountEntity gitAccount;

  @OneToMany(mappedBy = "superGroup", cascade = REMOVE)
  private Set<SubGroupEntity> subGroups = emptySet();

  @Builder
  private SuperGroupEntity(
      long remoteId,
      String code,
      String shortName,
      String name,
      String path,
      URL webUrl,
      @NonNull GitAccountEntity gitAccount,
      Set<SubGroupEntity> subGroups,
      Set<ProjectEntity> projects,
      String description
  ) {
    super(remoteId, code, shortName, name, path, webUrl, projects, description);
    this.gitAccount = gitAccount;
    this.subGroups = nonNull(subGroups) ? Set.copyOf(subGroups) : emptySet();
  }

  public Stream<SubGroupEntity> getSubGroups() {
    return nonNull(subGroups) ? subGroups.stream() : Stream.empty();
  }
}
