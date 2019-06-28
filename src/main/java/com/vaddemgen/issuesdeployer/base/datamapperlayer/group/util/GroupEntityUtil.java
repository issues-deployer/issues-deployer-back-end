package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.util;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.GroupEntity;

final class GroupEntityUtil {

  private GroupEntityUtil() {
  }

  static void mergeGroupEntity(Group from, GroupEntity to) {
    to.setRemoteId(from.getRemoteId());
    to.setCode(from.getCode());
    to.setShortName(from.getShortName());
    to.setName(from.getName());
    to.setPath(from.getPath());
    to.setWebUrl(from.getWebUrl());
    to.setDescription(from.getDescription());
  }
}

