package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SubGroupEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import java.util.List;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubGroupRepository extends GroupRepository<SubGroupEntity> {

  List<SubGroupEntity> findBySuperGroup(SuperGroupEntity entity);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("from SubGroup g where g.superGroup.id = :superGroupId")
  List<SubGroupEntity> findAllForUpdateBy(@Param("superGroupId") long superGroupId);
}
