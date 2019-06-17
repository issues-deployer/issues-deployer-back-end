package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

interface SubGroupEntityDataMapper {

  @Transactional
  void saveSubGroups(@NotNull SuperGroup superGroup, @NotNull List<SubGroup> subGroups);
}
