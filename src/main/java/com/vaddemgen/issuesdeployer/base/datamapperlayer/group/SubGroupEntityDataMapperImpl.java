package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.factory.SubGroupEntityFactory.createSubGroupEntity;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository.SubGroupRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository.SuperGroupRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class SubGroupEntityDataMapperImpl implements SubGroupEntityDataMapper {

  private final SuperGroupRepository superGroupRepository;
  private final SubGroupRepository subGroupRepository;

  public SubGroupEntityDataMapperImpl(
      @NotNull SuperGroupRepository superGroupRepository,
      @NotNull SubGroupRepository subGroupRepository
  ) {
    this.superGroupRepository = superGroupRepository;
    this.subGroupRepository = subGroupRepository;
  }

  @Override
  @Transactional
  public void saveSubGroups(@NotNull SuperGroup superGroup, @NotNull List<SubGroup> subGroups) {
    SuperGroupEntity superGroupEntity = superGroupRepository
        .findOneForShare(superGroup.getRemoteId())
        .orElseThrow(IllegalArgumentException::new); // TODO: change to ModelNotFoundException

    subGroupRepository.saveAll(
        subGroups.stream()
            .map(subGroup -> createSubGroupEntity(subGroup, superGroupEntity))
            .collect(Collectors.toList())
    );
  }
}
