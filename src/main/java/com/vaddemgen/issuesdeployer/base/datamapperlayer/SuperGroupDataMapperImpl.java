package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import static java.util.Collections.emptyList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClient;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClientFactory;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class SuperGroupDataMapperImpl implements SuperGroupDataMapper {

  private final GitClientFactory gitClientFactory;

  public SuperGroupDataMapperImpl(@NotNull GitClientFactory gitClientFactory) {
    this.gitClientFactory = gitClientFactory;
  }

  @NotNull
  @Override
  public List<SuperGroup> findSuperGroups(@NotNull GitAccount gitAccount) {
    // TODO: implement it.
    GitClient<GitAccount> gitClient = gitClientFactory.createGitClient(gitAccount);
    try {
      return gitClient.findSuperGroups().collect(Collectors.toList());
    } catch (IOException | InterruptedException e) { // TODO: Handle the exceptions
      e.printStackTrace();
    }
    return emptyList();
  }
}
