package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.GroupEntity;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository<T extends GroupEntity> extends JpaRepository<T, Long> {

  @Lock(LockModeType.PESSIMISTIC_READ)
  @Query("from GroupEntity g where g.id = :id")
  Optional<T> findOneForShare(@Param("id") long id);
}
