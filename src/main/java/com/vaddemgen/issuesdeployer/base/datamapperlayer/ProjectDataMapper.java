package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public interface ProjectDataMapper {

  Stream<Project> findProjectsByGroup(@NotNull GitAccount gitAccount, @NotNull Group superGroup);

  Stream<Project> saveProjects(Group group, Collection<Project> projects);

  Stream<Project> mergeProjects(Group projectsOwner, List<Project> projects);

  Stream<Project> findProjectsBy(GitAccount gitAccount);
}
