package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import static java.util.stream.Collectors.toList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SubGroupFactory;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SuperGroupFactory;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository.SuperGroupRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClient;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClientFactory;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class SuperGroupDataMapperImpl implements SuperGroupDataMapper {

  private final GitClientFactory gitClientFactory;
  private final SuperGroupRepository superGroupRepository;
  private final SuperGroupEntityDataMapper superGroupEntityDataMapper;

  public SuperGroupDataMapperImpl(
      @NotNull GitClientFactory gitClientFactory,
      @NotNull SuperGroupRepository superGroupRepository,
      @NotNull SuperGroupEntityDataMapper superGroupEntityDataMapper
  ) {
    this.gitClientFactory = gitClientFactory;
    this.superGroupRepository = superGroupRepository;
    this.superGroupEntityDataMapper = superGroupEntityDataMapper;
  }

  @Override
  public Stream<SuperGroup> findSuperGroups(@NotNull GitAccount gitAccount) {
    // TODO: implement it.

    List<SuperGroupEntity> groupEntities = superGroupRepository
        .findAllByGitAccountId(gitAccount.getId().orElseThrow(IllegalArgumentException::new));

    if (!groupEntities.isEmpty()) {
      return groupEntities.stream()
          .map(groupEntity ->
              SuperGroupFactory.createSuperGroup(groupEntity)
                  .clonePartially()
                  .subGroups(
                      groupEntity.getSubGroups()
                          .map(SubGroupFactory::createSubGroup)
                          .collect(toList())
                  ).build()
          );
    }

    GitClient<GitAccount> gitClient = gitClientFactory.createGitClient(gitAccount);
    try {
      List<SuperGroup> superGroups = gitClient.findSuperGroups().collect(toList());

      superGroupEntityDataMapper.saveSuperGroups(gitAccount, superGroups);

      return superGroups.stream();
    } catch (IOException | InterruptedException e) { // TODO: Handle the exceptions
      e.printStackTrace();
    }
    return Stream.empty();
  }
}
