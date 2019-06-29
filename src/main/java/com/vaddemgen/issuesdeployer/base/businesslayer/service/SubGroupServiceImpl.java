package com.vaddemgen.issuesdeployer.base.businesslayer.service;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.SubGroupDataMapper;
import java.util.List;
import java.util.stream.Stream;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public final class SubGroupServiceImpl implements SubGroupService {

  private final SubGroupDataMapper subGroupDataMapper;

  public SubGroupServiceImpl(@NonNull SubGroupDataMapper subGroupDataMapper) {
    this.subGroupDataMapper = subGroupDataMapper;
  }

  @Override
  public Stream<SubGroup> getAllBy(@NonNull User user, long superGroupId) {
    return subGroupDataMapper.findAllBy(user, superGroupId);
  }

  @Override
  public Stream<SubGroup> mergeSubGroups(SuperGroup superGroup, List<SubGroup> subGroups) {
    return subGroupDataMapper.mergeSubGroups(superGroup, subGroups);
  }
}
