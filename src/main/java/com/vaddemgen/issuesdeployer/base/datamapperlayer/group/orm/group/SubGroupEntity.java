package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.gitaccount.GitAccountEntity;
import java.net.URL;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue(SubGroupEntity.TYPE_SUB_GROUP)
public final class SubGroupEntity extends GroupEntity {

  static final String TYPE_SUB_GROUP = "SUB_GROUP";

  private static final long serialVersionUID = -8837164336737806711L;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "parent_id")
  private SuperGroupEntity superGroup;

  @Builder
  public SubGroupEntity(
      @NotNull long remoteId,
      @NotNull GitAccountEntity gitAccount,
      @NotNull SuperGroupEntity superGroup,
      @NotNull String code,
      @NotNull String shortName,
      String name,
      String path,
      URL webUrl,
      String description,
      Long id
  ) {
    super(id, remoteId, gitAccount, code, name, shortName, path, webUrl, description);
    this.superGroup = superGroup;
  }
}
