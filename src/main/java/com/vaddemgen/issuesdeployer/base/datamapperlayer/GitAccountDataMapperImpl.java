package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.gitlabclient.businesslayer.GitLabAccount;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public final class GitAccountDataMapperImpl implements GitAccountDataMapper {

  @Override
  public Stream<GitAccount> findGitAccountsByUser(@NotNull User user) {
    // TODO: Implement it.
    return Stream.of(
        GitLabAccount.builder()
            .id(1L)
            .token("uuTciEGmcQPJjHuUwypb")
            .build()
    );
  }
}
