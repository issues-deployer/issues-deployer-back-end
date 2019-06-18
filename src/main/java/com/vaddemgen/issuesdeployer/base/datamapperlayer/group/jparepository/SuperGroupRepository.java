package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SuperGroupRepository extends GroupRepository<SuperGroupEntity> {

  @Query("from SuperGroupEntity g left join fetch g.subGroups where g.gitAccount.id = :gitAccountId")
  List<SuperGroupEntity> findAllByGitAccountId(@Param("gitAccountId") long gitAccountId);
}
