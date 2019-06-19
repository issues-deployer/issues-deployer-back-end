package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SuperGroupEntityFactory.createSuperGroupEntity;
import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SuperGroupFactory.createSuperGroup;
import static java.util.stream.Collectors.toList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SubGroupFactory;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository.SuperGroupRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository.GitAccountRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.gitaccount.GitAccountEntity;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClient;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClientFactory;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SuperGroupDataMapperImpl implements SuperGroupDataMapper {

  private final GitClientFactory gitClientFactory;
  private final SuperGroupRepository superGroupRepository;
  private final GitAccountRepository gitAccountRepository;

  // Self autowired
  private SuperGroupDataMapperImpl self;

  public SuperGroupDataMapperImpl(
      GitClientFactory gitClientFactory,
      SuperGroupRepository superGroupRepository,
      GitAccountRepository gitAccountRepository) {
    this.gitClientFactory = gitClientFactory;
    this.superGroupRepository = superGroupRepository;
    this.gitAccountRepository = gitAccountRepository;
  }

  private static SuperGroup createSuperGroupWithDeps(SuperGroupEntity groupEntity) {
    return createSuperGroup(groupEntity)
        .clonePartially()
        .subGroups(
            groupEntity.getSubGroups()
                .map(SubGroupFactory::createSubGroup)
                .collect(toList())
        ).build();
  }

  @Autowired
  public void setSelf(SuperGroupDataMapperImpl self) {
    this.self = self;
  }

  @Override
  public Stream<SuperGroup> findSuperGroups(@NotNull GitAccount gitAccount) {
    // TODO: implement it.

    List<SuperGroupEntity> groupEntities = superGroupRepository
        .findAllByGitAccountId(gitAccount.getId().orElseThrow(IllegalArgumentException::new));

    if (!groupEntities.isEmpty()) {
      return groupEntities.stream()
          .map(SuperGroupDataMapperImpl::createSuperGroupWithDeps);
    }

    GitClient<GitAccount> gitClient = gitClientFactory.createGitClient(gitAccount);
    try {
      List<SuperGroup> superGroups = gitClient.findSuperGroups().collect(toList());

      if (!superGroups.isEmpty()) {
        return self.saveSuperGroups(gitAccount, superGroups);
      }
    } catch (IOException | InterruptedException e) { // TODO: Handle the exceptions
      e.printStackTrace();
    }
    return Stream.empty();
  }

  @Transactional
  public Stream<SuperGroup> saveSuperGroups(@NotNull GitAccount gitAccount,
      @NotNull List<SuperGroup> superGroups) {
    GitAccountEntity superGroupEntity = gitAccountRepository
        .findOneForShare(gitAccount.getId().orElseThrow())
        .orElseThrow(); // TODO: change to ModelNotFoundException

    return superGroupRepository
        .saveAll(
            superGroups.stream()
                .map(subGroup -> createSuperGroupEntity(subGroup, superGroupEntity))
                .collect(Collectors.toList())
        )
        .stream()
        .map(SuperGroupDataMapperImpl::createSuperGroupWithDeps);
  }
}
