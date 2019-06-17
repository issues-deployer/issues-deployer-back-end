package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface GroupRepository<T extends GroupEntity> extends JpaRepository<T, Long> {

}
