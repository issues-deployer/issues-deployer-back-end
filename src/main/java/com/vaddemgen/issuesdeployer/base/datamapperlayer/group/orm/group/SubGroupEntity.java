package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.ProjectEntity;
import java.net.URL;
import java.util.Set;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "SubGroup")
@DiscriminatorValue(SubGroupEntity.TYPE_SUB_GROUP)
public final class SubGroupEntity extends GroupEntity {

  static final String TYPE_SUB_GROUP = "SUB_GROUP";

  private static final long serialVersionUID = -8837164336737806711L;

  @NonNull
  @NotNull
  @ManyToOne
  @JoinColumn(name = "parent_id")
  private SuperGroupEntity superGroup;

  @Builder
  public SubGroupEntity(
      long remoteId,
      @NonNull String code,
      @NonNull String shortName,
      @NonNull String name,
      @NonNull String path,
      @NonNull Set<ProjectEntity> projects,
      URL webUrl,
      String description,
      @NonNull SuperGroupEntity superGroup
  ) {
    super(remoteId, code, shortName, name, path, projects, webUrl, description);
    this.superGroup = superGroup;
  }
}
