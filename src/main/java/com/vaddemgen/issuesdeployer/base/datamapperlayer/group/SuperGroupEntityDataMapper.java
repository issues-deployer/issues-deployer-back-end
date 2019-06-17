package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface SuperGroupEntityDataMapper {

  void saveSuperGroups(@NotNull GitAccount gitAccount, @NotNull List<SuperGroup> superGroups);
}
