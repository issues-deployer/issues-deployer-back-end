package com.vaddemgen.issuesdeployer.base.businesslayer.service;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.GitAccountDataMapper;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.IssueDataMapper;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.ProjectDataMapper;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.SubGroupDataMapper;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.SuperGroupDataMapper;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public final class SuperGroupServiceImpl implements SuperGroupService {

  private final GitAccountDataMapper gitAccountDataMapper;
  private final SuperGroupDataMapper superGroupDataMapper;
  private final SubGroupDataMapper subGroupDataMapper;
  private final ProjectDataMapper projectDataMapper;
  private final IssueDataMapper issueDataMapper;

  public SuperGroupServiceImpl(
      @NotNull GitAccountDataMapper gitAccountDataMapper,
      @NotNull SuperGroupDataMapper superGroupDataMapper,
      @NotNull SubGroupDataMapper subGroupDataMapper,
      @NotNull ProjectDataMapper projectDataMapper,
      @NotNull IssueDataMapper issueDataMapper
  ) {
    this.gitAccountDataMapper = gitAccountDataMapper;
    this.superGroupDataMapper = superGroupDataMapper;
    this.subGroupDataMapper = subGroupDataMapper;
    this.projectDataMapper = projectDataMapper;
    this.issueDataMapper = issueDataMapper;
  }

  @Override
  public Stream<SuperGroup> findSuperGroupsTree(@NotNull User user) {
    return gitAccountDataMapper.findGitAccountsByUser(user)
        .parallel()
        .flatMap(superGroupDataMapper::findSuperGroups)
        .map(this::fillSuperGroup);
  }

  private SuperGroup fillSuperGroup(@NotNull SuperGroup superGroup) {
    return superGroup.clonePartially()
        .subGroups(
            subGroupDataMapper.findSubGroupsBySuperGroup(superGroup)
                .parallel()
                .map(this::fillSubGroup)
                .collect(Collectors.toList())
        )
        .projects(
            projectDataMapper.findProjectsByGroup(superGroup)
                .parallel()
                .map(this::fillProject)
                .collect(Collectors.toList())
        ).build();
  }

  private SubGroup fillSubGroup(@NotNull SubGroup subGroup) {
    return subGroup.clonePartially()
        .projects(
            projectDataMapper.findProjectsByGroup(subGroup)
                .map(this::fillProject)
                .collect(Collectors.toList())
        ).build();
  }

  private Project fillProject(@NotNull Project project) {
    return project.clonePartially()
        .issues(issueDataMapper.findIssuesByProject(project).collect(Collectors.toList()))
        .build();
  }
}
