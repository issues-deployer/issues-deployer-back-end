package com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.IssueEntity;
import java.util.List;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueRepository extends JpaRepository<IssueEntity, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("from Issue i where i.project.id = :projectId")
  List<IssueEntity> findAllForUpdateBy(@Param("projectId") long projectId);

  @Query(
      "select i "
          + "from User u "
          + "join u.gitAccounts ga "
          + "join ga.superGroups s "
          + "left join s.subGroups g "
          + "join Project p on p.group.id = s.id or p.group.id = g.id "
          + "join p.issues i "
          + "where u.id = :userId and p.id = :projectId"
  )
  List<IssueEntity> findAllBy(
      @Param("userId") long userId,
      @Param("projectId") long projectId
  );
}
