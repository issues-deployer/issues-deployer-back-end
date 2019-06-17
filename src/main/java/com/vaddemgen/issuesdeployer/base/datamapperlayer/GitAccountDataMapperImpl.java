package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.factory.GitAccountFactory;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository.GitAccountRepository;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public final class GitAccountDataMapperImpl implements GitAccountDataMapper {

  private final GitAccountRepository gitAccountRepository;

  public GitAccountDataMapperImpl(@NotNull GitAccountRepository gitAccountRepository) {
    this.gitAccountRepository = gitAccountRepository;
  }

  @Override
  public Stream<GitAccount> findGitAccountsByUser(@NotNull User user) {
    return user.getGitAccounts().count() > 0
        ? user.getGitAccounts() :
        gitAccountRepository.findAllByUserId(user.getId().orElseThrow(IllegalAccessError::new))
            .stream()
            .map(GitAccountFactory::createGitAccount);
  }
}
