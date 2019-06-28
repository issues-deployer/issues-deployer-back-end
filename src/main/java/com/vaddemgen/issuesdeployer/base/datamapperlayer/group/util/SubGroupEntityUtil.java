package com.vaddemgen.issuesdeployer.base.datamapperlayer.group.util;

import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.util.GroupEntityUtil.mergeGroupEntity;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.SubGroupEntity;

public final class SubGroupEntityUtil {

  private SubGroupEntityUtil() {
  }

  public static void mergeSubGroupEntity(SubGroup from, SubGroupEntity to) {
    mergeGroupEntity(from, to);
  }
}
