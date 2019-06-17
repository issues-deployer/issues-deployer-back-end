package com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
