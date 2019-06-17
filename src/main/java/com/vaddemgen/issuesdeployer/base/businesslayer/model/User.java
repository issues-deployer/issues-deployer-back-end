package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@AllArgsConstructor
@Builder
public final class User implements DomainModel {

  private static final long serialVersionUID = -3139039181217327308L;
  @Nullable
  private final Long id;

  @NotNull
  private final String username;

  @NotNull
  private final String firstName;

  @NotNull
  private final String lastName;

  @NotNull
  private final List<GitAccount> gitAccounts;

  public Stream<GitAccount> getGitAccounts() {
    return gitAccounts.stream();
  }

  public Optional<Long> getId() {
    return Optional.ofNullable(id);
  }

  @Override
  public UserBuilder clonePartially() {
    return builder()
        .id(id)
        .username(username)
        .firstName(firstName)
        .lastName(lastName)
        .gitAccounts(gitAccounts);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id)
        && username.equals(user.username)
        && firstName.equals(user.firstName)
        && lastName.equals(user.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username);
  }
}
