package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface SuperGroupDataMapper {

  @NotNull
  List<SuperGroup> findSuperGroups(@NotNull GitAccount gitAccount);
}
