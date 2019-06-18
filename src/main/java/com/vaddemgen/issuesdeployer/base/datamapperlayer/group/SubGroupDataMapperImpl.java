package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SubGroupEntityFactory.createSubGroupEntity;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SubGroupDataMapperImpl implements SubGroupDataMapper {

  private final GitClientFactory gitClientFactory;
  private final SubGroupRepository subGroupRepository;
  private final SuperGroupRepository superGroupRepository;

  // Self autowired
  private SubGroupDataMapperImpl self;

  public SubGroupDataMapperImpl(
      @NotNull GitClientFactory gitClientFactory,
      @NotNull SubGroupRepository subGroupRepository,
      @NotNull SuperGroupRepository superGroupRepository) {
    this.gitClientFactory = gitClientFactory;
    this.subGroupRepository = subGroupRepository;
    this.superGroupRepository = superGroupRepository;
  }

  @Autowired
  public void setSelf(SubGroupDataMapperImpl subGroupDataMapper) {
    this.self = subGroupDataMapper;
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
        self.saveSubGroups(gitAccount, superGroup, subGroups);
      }

      return subGroups.stream();
    } catch (IOException | InterruptedException e) { // TODO: Handle the exceptions
      e.printStackTrace();
    }
    return Stream.empty();
  }

  @Override
  @Transactional
  public void saveSubGroups(
      @NotNull GitAccount gitAccount,
      @NotNull SuperGroup superGroup,
      @NotNull List<SubGroup> subGroups
  ) {
    SuperGroupEntity superGroupEntity = superGroupRepository
        .findOneForShare(superGroup.getId().orElseThrow(IllegalArgumentException::new))
        .orElseThrow(IllegalArgumentException::new); // TODO: change to ModelNotFoundException

    subGroupRepository.saveAll(
        subGroups.stream()
            .map(subGroup -> createSubGroupEntity(subGroup, superGroupEntity))
            .collect(toList())
    );
  }
}
