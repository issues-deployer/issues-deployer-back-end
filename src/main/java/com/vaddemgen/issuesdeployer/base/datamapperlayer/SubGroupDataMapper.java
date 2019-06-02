package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public interface SubGroupDataMapper {

  Stream<SubGroup> findSubGroupsBySuperGroup(@NotNull SuperGroup superGroup);
}
