package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import static java.util.stream.Collectors.toList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClientFactory;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public final class ProjectDataMapperImpl implements ProjectDataMapper {

  private final GitClientFactory gitClientFactory;

  public ProjectDataMapperImpl(@NotNull GitClientFactory gitClientFactory) {
    this.gitClientFactory = gitClientFactory;
  }

  @Override
  public Stream<Project> findProjectsByGroup(
      @NotNull GitAccount gitAccount,
      @NotNull Group group
  ) {

    var gitClient = gitClientFactory.createGitClient(gitAccount);
    try {
      List<Project> subGroups = gitClient.findProjects(group).collect(toList());

//      if (!subGroups.isEmpty()) {
//        subGroupEntityDataMapper.saveSubGroups(
//            group.clonePartially()
//                .gitAccount(gitAccount)
//                .build(),
//            subGroups
//        );
//      }

      return subGroups.stream();
    } catch (IOException | InterruptedException e) { // TODO: Handle the exceptions
      e.printStackTrace();
    }
    return Stream.empty();
  }
}
