package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@AllArgsConstructor
@Builder
public final class User implements DomainModel {

  private final long id;

  @NotNull
  private final String username;

  @NotNull
  private final String firstName;

  @NotNull
  private final String lastName;

  @NotNull
  @Builder.Default
  private final List<GitAccount> gitAccounts;

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
}
