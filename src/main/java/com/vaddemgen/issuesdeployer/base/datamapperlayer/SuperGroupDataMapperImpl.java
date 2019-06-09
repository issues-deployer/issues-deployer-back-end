package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClient;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClientFactory;
import java.io.IOException;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public final class SuperGroupDataMapperImpl implements SuperGroupDataMapper {

  @NotNull
  @Override
  public Stream<SuperGroup> findSuperGroups(@NotNull GitAccount gitAccount) {
    // TODO: implement it.
    GitClient<GitAccount> gitClient = GitClientFactory.createGitClient(gitAccount);
    try {
      return gitClient.findSuperGroups();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return Stream.empty();
  }
}
