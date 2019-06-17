package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import static java.util.stream.Collectors.toList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SubGroupFactory;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository.SubGroupRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository.SuperGroupRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClientFactory;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public final class SubGroupDataMapperImpl implements SubGroupDataMapper {

  private final GitClientFactory gitClientFactory;
  private final SubGroupEntityDataMapper subGroupEntityDataMapper;
  private final SubGroupRepository subGroupRepository;
  private final SuperGroupRepository superGroupRepository;

  public SubGroupDataMapperImpl(
      @NotNull GitClientFactory gitClientFactory,
      @NotNull SubGroupEntityDataMapper subGroupEntityDataMapper,
      @NotNull SubGroupRepository subGroupRepository,
      @NotNull SuperGroupRepository superGroupRepository) {
    this.gitClientFactory = gitClientFactory;
    this.subGroupEntityDataMapper = subGroupEntityDataMapper;
    this.subGroupRepository = subGroupRepository;
    this.superGroupRepository = superGroupRepository;
  }

  @Override
  public Stream<SubGroup> findSubGroupsBySuperGroup(
      @NotNull GitAccount gitAccount,
      @NotNull SuperGroup superGroup
  ) {
    // TODO: implement it.

    if (superGroup.getSubGroups().count() > 0) {
      return superGroup.getSubGroups();
    }

    SuperGroupEntity superGroupEntity = superGroup.getId()
        .flatMap(superGroupRepository::findById)
        .orElseThrow(IllegalArgumentException::new);

    var groupEntities = subGroupRepository.findBySuperGroup(superGroupEntity);

    if (!groupEntities.isEmpty()) {
      return groupEntities.stream()
          .map(SubGroupFactory::createSubGroup);
    }

    var gitClient = gitClientFactory.createGitClient(gitAccount);
    try {
      List<SubGroup> subGroups = gitClient.findSubGroups(superGroup).collect(toList());

      if (!subGroups.isEmpty()) {
        subGroupEntityDataMapper.saveSubGroups(
            superGroup.clonePartially()
                .gitAccount(gitAccount)
                .build(),
            subGroups
        );
      }

      return subGroups.stream();
    } catch (IOException | InterruptedException e) { // TODO: Handle the exceptions
      e.printStackTrace();
    }
    return Stream.empty();
  }
}
