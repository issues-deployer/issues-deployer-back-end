package com.vaddemgen.issuesdeployer.base.businesslayer.service;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.ProjectDataMapper;
import java.util.List;
import java.util.stream.Stream;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public final class ProjectServiceImpl implements ProjectService {

  private final ProjectDataMapper projectDataMapper;

  public ProjectServiceImpl(@NonNull ProjectDataMapper projectDataMapper) {
    this.projectDataMapper = projectDataMapper;
  }

  @Override
  public Stream<Project> mergeProjects(Group projectsOwner, List<Project> projects) {
    return projectDataMapper.mergeProjects(projectsOwner, projects);
  }

  @Override
  public Stream<Project> getProjectsBy(@NonNull GitAccount gitAccount) {
    return projectDataMapper.findProjectsBy(gitAccount);
  }
}
