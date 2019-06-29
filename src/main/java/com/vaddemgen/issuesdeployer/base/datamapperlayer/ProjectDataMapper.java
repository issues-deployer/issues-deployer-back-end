package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import java.util.List;
import java.util.stream.Stream;

public interface ProjectDataMapper {

  Stream<Project> mergeProjects(Group projectsOwner, List<Project> projects);

  Stream<Project> findProjectsBy(GitAccount gitAccount);

  Stream<Project> findProjectsBy(User user, long groupId);
}
