package com.vaddemgen.issuesdeployer.base.businesslayer.service;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.ProjectDataMapper;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.SubGroupDataMapper;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.SuperGroupDataMapper;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public final class SuperGroupServiceImpl implements SuperGroupService {

  private final SuperGroupDataMapper superGroupDataMapper;

  private final SubGroupDataMapper subGroupDataMapper;
  private final ProjectDataMapper projectDataMapper;

  public SuperGroupServiceImpl(
      @NotNull SuperGroupDataMapper superGroupDataMapper,
      @NotNull SubGroupDataMapper subGroupDataMapper,
      @NotNull ProjectDataMapper projectDataMapper
  ) {
    this.superGroupDataMapper = superGroupDataMapper;
    this.subGroupDataMapper = subGroupDataMapper;
    this.projectDataMapper = projectDataMapper;
  }

  @Override
  public Stream<SuperGroup> findSuperGroupsTree(@NotNull User user) {
    return user.getGitAccounts()
        .parallel()
        .map(gitAccount -> gitAccount.clonePartially().user(user).build())
        .flatMap(superGroupDataMapper::findSuperGroups)
        .map(this::fillSuperGroup);
  }

  @NotNull
  private SuperGroup fillSuperGroup(SuperGroup superGroup) {
    return superGroup.clonePartially()
        .subGroups(
            subGroupDataMapper.findSubGroupsBySuperGroup(superGroup)
                .map(this::fillSubGroup)
                .collect(Collectors.toList())
        )
        .projects(
            projectDataMapper.findProjectsByGroup(superGroup)
                .collect(Collectors.toList())

        ).build();
  }

  @NotNull
  private SubGroup fillSubGroup(SubGroup subGroup) {
    return subGroup.clonePartially()
        .projects(
            projectDataMapper.findProjectsByGroup(subGroup)
                .collect(Collectors.toList())
        ).build();
  }
}
