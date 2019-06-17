package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SubGroupEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;
import java.util.List;

public interface SubGroupRepository extends GroupRepository<SubGroupEntity> {

  List<SubGroupEntity> findBySuperGroup(SuperGroupEntity entity);
}
