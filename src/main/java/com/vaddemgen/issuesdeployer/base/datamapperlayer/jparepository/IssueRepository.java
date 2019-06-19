package com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.IssueEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<IssueEntity, Long> {

  List<IssueEntity> findAllByProjectId(long projectId);
}
