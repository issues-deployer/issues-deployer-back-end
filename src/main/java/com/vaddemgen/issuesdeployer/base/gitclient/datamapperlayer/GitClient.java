package com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.io.IOException;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

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

  /**
   * Finds sub groups for the user of the git account by the super group.
   *
   * @param superGroup The super group of sub groups
   * @throws IOException          - if an I/O error occurs when sending or receiving
   * @throws InterruptedException - if the operation is interrupted
   */
  Stream<SubGroup> findSubGroups(@NotNull SuperGroup superGroup)
      throws IOException, InterruptedException;

  /**
   * Finds git projects by a group.
   *
   * @param group The group of projects.
   * @throws IOException          - if an I/O error occurs when sending or receiving
   * @throws InterruptedException - if the operation is interrupted
   */
  Stream<Project> findProjects(@NotNull Group group) throws IOException, InterruptedException;
}
