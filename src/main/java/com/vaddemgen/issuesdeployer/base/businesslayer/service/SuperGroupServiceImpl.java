package com.vaddemgen.issuesdeployer.base.businesslayer.service;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.SuperGroupDataMapper;
import java.util.List;
import java.util.stream.Stream;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public final class SuperGroupServiceImpl implements SuperGroupService {

  private final SuperGroupDataMapper superGroupDataMapper;

  public SuperGroupServiceImpl(@NonNull SuperGroupDataMapper superGroupDataMapper) {
    this.superGroupDataMapper = superGroupDataMapper;
  }

  @Override
  public Stream<SuperGroup> getAllBy(@NonNull User user) {
    return superGroupDataMapper.findAllBy(user);
  }

  @Override
  public Stream<SuperGroup> mergeSuperGroups(GitAccount gitAccount, List<SuperGroup> superGroups) {
    return superGroupDataMapper.mergeSuperGroups(gitAccount, superGroups);
  }
}
