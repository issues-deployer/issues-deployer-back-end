package com.vaddemgen.issuesdeployer.base.businesslayer.service;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.util.List;
import java.util.stream.Stream;

public interface SubGroupService {

  Stream<SubGroup> getAllBy(User user, long superGroupId);

  Stream<SubGroup> mergeSubGroups(SuperGroup superGroup, List<SubGroup> subGroups);
}
