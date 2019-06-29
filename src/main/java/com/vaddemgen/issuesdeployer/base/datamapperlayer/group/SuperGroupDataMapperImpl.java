package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SuperGroupEntityFactory.createSuperGroupEntity;
import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SuperGroupFactory.createSuperGroup;
import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.util.SuperGroupEntityUtil.mergeSuperGroupEntity;
import static java.util.stream.Collectors.toList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SubGroupFactory;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SuperGroupFactory;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository.SuperGroupRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.GroupEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository.GitAccountRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.gitaccount.GitAccountEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SuperGroupDataMapperImpl extends AbstractGroupDataMapper<SuperGroup, SuperGroupEntity>
    implements SuperGroupDataMapper {

  private final SuperGroupRepository superGroupRepository;
  private final GitAccountRepository gitAccountRepository;

  public SuperGroupDataMapperImpl(
      @NonNull SuperGroupRepository superGroupRepository,
      @NonNull GitAccountRepository gitAccountRepository
  ) {
    super(superGroupRepository);
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

  @Override
  public Stream<SuperGroup> findAllBy(User user) {
    return superGroupRepository.findAllByUserId(user.getId())
        .stream()
        .map(SuperGroupFactory::createSuperGroup);
  }

  @Transactional
  @Override
  public Stream<SuperGroup> mergeSuperGroups(GitAccount gitAccount, List<SuperGroup> superGroups) {
    GitAccountEntity gitAccountEntity = gitAccountRepository
        .findOneForShare(gitAccount.getId())
        .orElseThrow(); // TODO: change to ModelNotFoundException

    var superGroupEntities = superGroupRepository.findAllForUpdateBy(gitAccountEntity.getId());

    // Splitting the super group entities for deletion (TRUE) and update (FALSE).
    var map = splitGroupEntitiesForDeleteAndMerge(
        superGroupEntities,
        superGroups.stream().mapToLong(Group::getRemoteId).toArray()
    );

    // Deleting the entities.
    deleteGroupEntities(map.get(true));

    // Save the entities.
    return saveSuperGroupEntities(superGroups, map.get(false), gitAccountEntity);
  }

  private Stream<SuperGroup> saveSuperGroupEntities(
      List<SuperGroup> entitiesToSave,
      List<SuperGroupEntity> existedEntities,
      GitAccountEntity superGroupsOwner
  ) {
    List<SuperGroup> mergedSuperGroups = new ArrayList<>(entitiesToSave.size());

    var map = splitGroupForCreateAndUpdate(
        entitiesToSave,
        existedEntities.stream().mapToLong(GroupEntity::getRemoteId).toArray()
    );

    // Create entities.
    mergedSuperGroups.addAll(
        createSuperGroups(superGroupsOwner, map.get(true)).collect(toList())
    );

    // Update entities.
    mergedSuperGroups.addAll(
        updateSuperGroups(existedEntities, map.get(false)).collect(toList())
    );

    return mergedSuperGroups.stream();
  }

  private Stream<SuperGroup> updateSuperGroups(
      List<SuperGroupEntity> existedEntities,
      List<SuperGroup> data
  ) {
    return mapGroups(data, existedEntities)
        .entrySet()
        .stream()
        .map((entry) -> {
          SuperGroupEntity groupEntity = entry.getValue().orElseThrow();
          mergeSuperGroupEntity(entry.getKey(), groupEntity);
          return createSuperGroup(superGroupRepository.save(groupEntity));
        });
  }

  private Stream<SuperGroup> createSuperGroups(
      @NonNull GitAccountEntity gitAccountEntity,
      List<SuperGroup> superGroups
  ) {
    return superGroupRepository
        .saveAll(
            superGroups.stream()
                .map(subGroup -> createSuperGroupEntity(subGroup, gitAccountEntity))
                .collect(Collectors.toList())
        )
        .stream()
        .map(SuperGroupDataMapperImpl::createSuperGroupWithDeps);
  }
}
