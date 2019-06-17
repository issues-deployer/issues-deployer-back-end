package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group;

import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SubGroupEntity.TYPE_SUB_GROUP;
import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity.TYPE_SUPER_GROUP;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.DbEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.gitaccount.GitAccountEntity;
import java.net.URL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorFormula;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"group\"")
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
  @JoinColumn(name = "git_account_id")
  @NotNull
  private GitAccountEntity gitAccount;

  @Column
  @NotNull
  private String code;

  @Column
  private String name;

  @Column
  @NotNull
  private String shortName;

  @Column
  private String path;

  @Column
  private URL webUrl;

  @Column
  private String description;
}
