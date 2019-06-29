package com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository;

import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.ProjectEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

  @Lock(LockModeType.PESSIMISTIC_READ)
  @Query("from Project where id = :id")
  Optional<ProjectEntity> findOneForShare(@Param("id") long id);

  List<ProjectEntity> findAllByGroupId(long groupId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("from Project p where p.group.id = :groupId")
  List<ProjectEntity> findAllForUpdateBy(@Param("groupId") long groupId);

  @Query(
      value = "select p "
          + "from SuperGroup s "
          + "left join s.subGroups g "
          + "join Project p on p.group = s or p.group.id = g.id "
          + "where s.gitAccount.id = :gitAccountId"
  )
  List<ProjectEntity> findAllByGitAccountId(@Param("gitAccountId") long gitAccountId);

  @Query(
      value = "select p "
          + "from User u "
          + "join u.gitAccounts ga "
          + "join ga.superGroups s "
          + "left join s.subGroups g "
          + "join Project p on p.group.id = s.id or p.group.id = g.id "
          + "where u.id = :userId "
          + "and (s.id = :groupId or g.id = :groupId)"
  )
  List<ProjectEntity> findAllBy(
      @Param("userId") long userId,
      @Param("groupId") long groupId
  );
}
