package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SuperGroupRepository extends GroupRepository<SuperGroupEntity> {

  @Lock(LockModeType.PESSIMISTIC_READ)
  @Query("from SuperGroupEntity where remoteId = :remoteId")
  Optional<SuperGroupEntity> findOneForShare(@Param("remoteId") long remoteId);


  @Query("from SuperGroupEntity g left join fetch g.subGroups where g.gitAccount.id = :gitAccountId")
  List<SuperGroupEntity> findAllByGitAccountId(@Param("gitAccountId") long gitAccountId);
}
