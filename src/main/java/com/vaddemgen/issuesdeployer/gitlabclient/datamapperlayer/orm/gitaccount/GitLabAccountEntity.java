package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.orm.gitaccount;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.UserEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.gitaccount.GitAccountEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "git_lab_account")
public final class GitLabAccountEntity extends GitAccountEntity {

  private static final long serialVersionUID = 7234286120840850058L;

  @Column
  @NotBlank
  @NotNull
  private String token;

  @Column
  private boolean isApproved;

  @Builder
  public GitLabAccountEntity(
      @NotNull UserEntity user,
      @NotBlank String token,
      boolean isApproved
  ) {
    super(user);
    this.token = token;
  }
}
