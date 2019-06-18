package com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.ProjectEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

  List<ProjectEntity> findAllByGroupId(long groupId);
}
