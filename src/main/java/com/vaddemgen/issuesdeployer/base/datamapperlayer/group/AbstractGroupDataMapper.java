package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toMap;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository.GroupRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.GroupEntity;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.LongStream;

abstract class AbstractGroupDataMapper<G extends Group, E extends GroupEntity> {

  private final GroupRepository<E> groupRepository;

  public AbstractGroupDataMapper(GroupRepository<E> groupRepository) {
    this.groupRepository = groupRepository;
  }

  void deleteGroupEntities(List<E> entitiesForDeletion) {
    groupRepository.deleteAll(entitiesForDeletion);
  }

  /**
   * <p>Maps groups and the related group entities by {@link Group#getRemoteId()}.</p>
   */
  final Map<G, Optional<E>> mapGroups(List<G> groups, List<E> groupEntities) {
    return groups.stream()
        .collect(toMap(
            group -> group,
            group ->
                groupEntities.stream()
                    .filter(entity -> group.getRemoteId() == entity.getRemoteId())
                    .findAny()
        ));
  }

  /**
   * Splits the group entities for deletion (TRUE) and update (FALSE).
   */
  final Map<Boolean, List<E>> splitGroupEntitiesForDeleteAndMerge(
      List<E> groupEntities,
      long[] remoteIds
  ) {
    return groupEntities.stream()
        .collect(partitioningBy(
            groupEntity -> LongStream.of(remoteIds)
                .noneMatch(remoteId -> remoteId == groupEntity.getRemoteId())
        ));
  }

  /**
   * Splits the group models for create (TRUE) and update (FALSE).
   */
  final Map<Boolean, List<G>> splitGroupForCreateAndUpdate(
      List<G> groups,
      long[] existedRemoteIds
  ) {
    return groups.stream()
        .collect(partitioningBy(
            group -> LongStream.of(existedRemoteIds)
                .noneMatch(remoteId -> remoteId == group.getRemoteId())
        ));
  }
}
