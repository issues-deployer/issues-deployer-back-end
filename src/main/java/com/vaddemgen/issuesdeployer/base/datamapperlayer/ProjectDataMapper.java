package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import java.util.Collection;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectDataMapper {

  Stream<Project> findProjectsByGroup(@NotNull GitAccount gitAccount, @NotNull Group superGroup);

  @Transactional
  Stream<Project> saveProjects(GitAccount gitAccount, Group group, Collection<Project> projects);
}
