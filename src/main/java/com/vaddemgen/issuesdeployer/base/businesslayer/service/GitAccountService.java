package com.vaddemgen.issuesdeployer.base.businesslayer.service;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import java.util.stream.Stream;

public interface GitAccountService {

  Stream<GitAccount> getAllGitAccounts();
}
