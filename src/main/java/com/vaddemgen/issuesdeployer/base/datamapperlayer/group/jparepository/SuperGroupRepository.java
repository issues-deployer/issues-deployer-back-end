package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import java.util.List;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SuperGroupRepository extends GroupRepository<SuperGroupEntity> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("from SuperGroup g where g.gitAccount.id = :gitAccountId")
  List<SuperGroupEntity> findAllForUpdateBy(@Param("gitAccountId") long gitAccountId);

  @Query("select s from User u join u.gitAccounts g join g.superGroups s where u.id = :userId")
  List<SuperGroupEntity> findAllByUserId(@Param("userId") long userId);
}
