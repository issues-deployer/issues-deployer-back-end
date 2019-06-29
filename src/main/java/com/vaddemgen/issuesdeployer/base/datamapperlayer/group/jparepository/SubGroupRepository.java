package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SubGroupEntity;
import java.util.List;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubGroupRepository extends GroupRepository<SubGroupEntity> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("from SubGroup g where g.superGroup.id = :superGroupId")
  List<SubGroupEntity> findAllForUpdateBy(@Param("superGroupId") long superGroupId);

  @Query("select g "
      + "from User u "
      + "join u.gitAccounts ga "
      + "join ga.superGroups s "
      + "join s.subGroups g "
      + "where u.id = :userId "
      + "and s.id = :superGroupId")
  List<SubGroupEntity> findAllBy(
      @Param("userId") long userId,
      @Param("superGroupId") long superGroupId
  );
}
