package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SubGroupEntityFactory.createSubGroupEntity;
import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SubGroupFactory.createSubGroup;
import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.util.SubGroupEntityUtil.mergeSubGroupEntity;
import static java.util.stream.Collectors.toList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SubGroupFactory;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository.SubGroupRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository.SuperGroupRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.GroupEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SubGroupEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SubGroupDataMapperImpl extends AbstractGroupDataMapper<SubGroup, SubGroupEntity>
    implements SubGroupDataMapper {

  private final SubGroupRepository subGroupRepository;
  private final SuperGroupRepository superGroupRepository;

  public SubGroupDataMapperImpl(
      @NonNull SubGroupRepository subGroupRepository,
      @NonNull SuperGroupRepository superGroupRepository
  ) {
    super(subGroupRepository);
    this.subGroupRepository = subGroupRepository;
    this.superGroupRepository = superGroupRepository;
  }

  @Override
  public Stream<SubGroup> findAllBy(User user, long superGroupId) {
    return subGroupRepository.findAllBy(user.getId(), superGroupId)
        .stream()
        .map(SubGroupFactory::createSubGroup);
  }

  @Transactional
  @Override
  public Stream<SubGroup> mergeSubGroups(SuperGroup superGroup, List<SubGroup> subGroups) {
    SuperGroupEntity superGroupEntity = superGroupRepository
        .findOneForShare(superGroup.getId())
        .orElseThrow(); // TODO: change to ModelNotFoundException

    var subGroupEntities = subGroupRepository.findAllForUpdateBy(superGroup.getId());

    // Splitting the super group entities for deletion (TRUE) and update (FALSE).
    var map = splitGroupEntitiesForDeleteAndMerge(
        subGroupEntities,
        subGroups.stream().mapToLong(Group::getRemoteId).toArray()
    );

    // Deleting the entities.
    deleteGroupEntities(map.get(true));

    // Save the entities.
    return saveSubGroupEntities(subGroups, map.get(false), superGroupEntity);
  }

  private Stream<SubGroup> saveSubGroupEntities(
      List<SubGroup> modelsToSave,
      List<SubGroupEntity> existedEntities,
      SuperGroupEntity subGroupsOwner
  ) {
    List<SubGroup> mergedSuperGroups = new ArrayList<>(modelsToSave.size());

    var map = splitGroupForCreateAndUpdate(
        modelsToSave,
        existedEntities.stream().mapToLong(GroupEntity::getRemoteId).toArray()
    );

    // Create entities.
    mergedSuperGroups.addAll(
        createSubGroups(map.get(true), subGroupsOwner).collect(toList())
    );

    // Update entities.
    mergedSuperGroups.addAll(
        updateSubGroups(existedEntities, map.get(false)).collect(toList())
    );

    return mergedSuperGroups.stream();
  }

  private Stream<SubGroup> updateSubGroups(
      List<SubGroupEntity> existedEntities,
      List<SubGroup> data
  ) {
    return mapGroups(data, existedEntities)
        .entrySet()
        .stream()
        .map((entry) -> {
          SubGroupEntity groupEntity = entry.getValue().orElseThrow();
          mergeSubGroupEntity(entry.getKey(), groupEntity);
          return createSubGroup(subGroupRepository.save(groupEntity));
        });
  }

  private Stream<SubGroup> createSubGroups(
      @NonNull List<SubGroup> subGroups,
      @NonNull SuperGroupEntity subGroupsOwner
  ) {
    return subGroupRepository
        .saveAll(
            subGroups.stream()
                .map(subGroup -> createSubGroupEntity(subGroup, subGroupsOwner))
                .collect(toList())
        ).stream()
        .map(SubGroupFactory::createSubGroup);
  }
}
