package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public final class User implements DomainModel {

  private static final long serialVersionUID = -3139039181217327308L;

  private final long id;
  private final String username;
  private final String firstName;
  private final String lastName;
  private final List<GitAccount> gitAccounts;

  @Builder
  public User(
      long id,
      @NonNull String username,
      @NonNull String firstName,
      @NonNull String lastName,
      List<GitAccount> gitAccounts
  ) {
    this.id = id;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gitAccounts = nonNull(gitAccounts) ? List.copyOf(gitAccounts) : emptyList();
  }

  public Stream<GitAccount> getGitAccounts() {
    return gitAccounts.stream();
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
    return id == user.id
        && username.equals(user.username)
        && firstName.equals(user.firstName)
        && lastName.equals(user.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username);
  }
}
