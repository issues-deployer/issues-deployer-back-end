package com.vaddemgen.issuesdeployer.base.businesslayer.service;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.GitAccountDataMapper;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public final class GitAccountServiceImpl implements GitAccountService {

  private final GitAccountDataMapper gitAccountDataMapper;

  public GitAccountServiceImpl(GitAccountDataMapper gitAccountDataMapper) {
    if (gitAccountDataMapper == null) {
      throw new RuntimeException();
    }
    this.gitAccountDataMapper = gitAccountDataMapper;
  }

  @Override
  public Stream<GitAccount> getAllGitAccounts() {
    return gitAccountDataMapper.findAllGitAccounts().collect(Collectors.toList())
        .stream();
  }
}
