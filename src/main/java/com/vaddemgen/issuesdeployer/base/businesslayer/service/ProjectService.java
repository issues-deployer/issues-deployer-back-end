package com.vaddemgen.issuesdeployer.base.businesslayer.service;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import java.util.List;
import java.util.stream.Stream;

public interface ProjectService {

  Stream<Project> mergeProjects(Group projectsOwner, List<Project> projects);

  Stream<Project> getProjectsBy(GitAccount gitAccount);

  Stream<Project> getProjectsBy(User user, long groupId);
}
