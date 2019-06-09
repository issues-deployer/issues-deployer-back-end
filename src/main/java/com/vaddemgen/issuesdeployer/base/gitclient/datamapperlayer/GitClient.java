package com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Represents a git client for a git server.
 */
public interface GitClient<T extends GitAccount> {

  /**
   * Finds super groups for the user of the git account.
   *
   * @throws IOException          - if an I/O error occurs when sending or receiving
   * @throws InterruptedException - if the operation is interrupted
   */
  Stream<SuperGroup> findSuperGroups() throws IOException, InterruptedException;
}
