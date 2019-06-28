package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import java.util.List;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SuperGroupRepository extends GroupRepository<SuperGroupEntity> {

  @Query("from SuperGroup g left join fetch g.subGroups where g.gitAccount.id = :gitAccountId")
  List<SuperGroupEntity> findAllByGitAccountId(@Param("gitAccountId") long gitAccountId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("from SuperGroup g where g.gitAccount.id = :gitAccountId")
  List<SuperGroupEntity> findAllForUpdateBy(@Param("gitAccountId") long gitAccountId);
}
