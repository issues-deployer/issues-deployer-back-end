package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SuperGroupEntityFactory.createSuperGroupEntity;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository.SuperGroupRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository.GitAccountRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.gitaccount.GitAccountEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class SuperGroupEntityDataMapperImpl implements SuperGroupEntityDataMapper {

  private final SuperGroupRepository superGroupRepository;
  private final GitAccountRepository gitAccountRepository;

  public SuperGroupEntityDataMapperImpl(
      @NotNull SuperGroupRepository superGroupRepository,
      @NotNull GitAccountRepository gitAccountRepository
  ) {
    this.superGroupRepository = superGroupRepository;
    this.gitAccountRepository = gitAccountRepository;
  }

  @Override
  @Transactional
  public void saveSuperGroups(@NotNull GitAccount gitAccount,
      @NotNull List<SuperGroup> superGroups) {
    GitAccountEntity superGroupEntity = gitAccountRepository
        .findOneForShare(gitAccount.getId().orElseThrow(IllegalArgumentException::new))
        .orElseThrow(IllegalArgumentException::new); // TODO: change to ModelNotFoundException

    superGroupRepository.saveAll(
        superGroups.stream()
            .map(subGroup -> createSuperGroupEntity(subGroup, superGroupEntity))
            .collect(Collectors.toList())
    );
  }
}
