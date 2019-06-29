package com.vaddemgen.issuesdeployer.base.datamapperlayer.orm;

import static java.util.Objects.nonNull;
import static javax.persistence.CascadeType.REMOVE;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.gitaccount.GitAccountEntity;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "\"user\"")
public final class UserEntity implements DbEntity {

  private static final long serialVersionUID = -7778583818787733243L;

  @Id
  @GeneratedValue
  @Column(updatable = false, insertable = false)
  private Long id;

  @Column
  @NotBlank
  @NotNull
  @Size(min = 5, max = 64)
  private String username;

  @Column
  @NotBlank
  @NotNull
  @Size(min = 1, max = 64)
  private String firstName;

  @Column
  @NotBlank
  @NotNull
  @Size(min = 1, max = 64)
  private String lastName;

  @OneToMany(mappedBy = "user", cascade = REMOVE)
  private Set<GitAccountEntity> gitAccounts;

  @Builder
  public UserEntity(
      @NotNull String username,
      @NotNull String firstName,
      @NotNull String lastName,
      @NotNull Set<GitAccountEntity> gitAccounts
  ) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gitAccounts = Set.copyOf(gitAccounts);
  }

  public Optional<Long> getId() {
    return Optional.ofNullable(id);
  }

  public Stream<GitAccountEntity> getGitAccounts() {
    return nonNull(gitAccounts) ? gitAccounts.stream() : Stream.empty();
  }
}
