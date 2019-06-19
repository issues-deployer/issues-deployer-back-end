package com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.gitaccount.GitAccountEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GitAccountRepository extends JpaRepository<GitAccountEntity, Long> {

  @Lock(LockModeType.PESSIMISTIC_READ)
  @Query("from GitAccount where id = :id")
  Optional<GitAccountEntity> findOneForShare(@Param("id") long id);

  List<GitAccountEntity> findAllByUserId(long userId);
}
