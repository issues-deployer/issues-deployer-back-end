package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group;

import static java.util.Collections.emptySet;
import static java.util.Objects.nonNull;

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
@Entity
@DiscriminatorValue(SuperGroupEntity.TYPE_SUPER_GROUP)
public final class SuperGroupEntity extends GroupEntity {

  static final String TYPE_SUPER_GROUP = "SUPER_GROUP";

  private static final long serialVersionUID = -7739796240091110001L;

  @Nullable
  @OneToMany(mappedBy = "superGroup")
  private Set<SubGroupEntity> subGroups = emptySet();

  @Builder
  public SuperGroupEntity(
      @NotNull long remoteId,
      @NotNull GitAccountEntity gitAccount,
      @NotNull String code,
      @NotNull String shortName,
      @Nullable Set<SubGroupEntity> subGroups,
      String name,
      String path,
      URL webUrl,
      String description,
      Long id
  ) {
    super(id, remoteId, gitAccount, code, name, shortName, path, webUrl, description);
    this.subGroups = subGroups;
  }

  public Stream<SubGroupEntity> getSubGroups() {
    return nonNull(subGroups) ? subGroups.stream() : Stream.empty();
  }
}
