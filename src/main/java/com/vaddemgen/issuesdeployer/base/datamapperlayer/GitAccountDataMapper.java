package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public interface GitAccountDataMapper {

  Stream<GitAccount> findGitAccountsByUser(@NotNull User user);
}
