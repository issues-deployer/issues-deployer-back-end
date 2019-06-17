package com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.gitaccount;

import static javax.persistence.CascadeType.REMOVE;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.DbEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.UserEntity;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "git_account")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class GitAccountEntity implements DbEntity {

  private static final long serialVersionUID = -2858679641024099075L;

  @Id
  @GeneratedValue
  @Column(updatable = false, insertable = false)
  @Nullable
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @NotNull
  private UserEntity user;

  @OneToMany(mappedBy = "gitAccount", cascade = REMOVE)
  private Set<SuperGroupEntity> superGroups;

  public GitAccountEntity(@NotNull UserEntity user) {
    this.user = user;
  }
}
