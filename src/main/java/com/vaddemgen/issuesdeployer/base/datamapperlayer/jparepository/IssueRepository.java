package com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.IssueEntity;
import java.util.List;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IssueRepository extends JpaRepository<IssueEntity, Long> {

  List<IssueEntity> findAllByProjectId(long projectId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("from Issue i where i.project.id = :projectId")
  List<IssueEntity> findAllForUpdateBy(@Param("projectId") long projectId);
}
