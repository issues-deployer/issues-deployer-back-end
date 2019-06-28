package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.util;

import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.util.GroupEntityUtil.mergeGroupEntity;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SuperGroupEntity;

public final class SuperGroupEntityUtil {

  private SuperGroupEntityUtil() {
  }

  public static void mergeSuperGroupEntity(SuperGroup from, SuperGroupEntity to) {
    mergeGroupEntity(from, to);
  }
}
