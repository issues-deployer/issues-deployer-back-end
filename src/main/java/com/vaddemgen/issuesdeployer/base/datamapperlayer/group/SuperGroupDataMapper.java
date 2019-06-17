package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public interface SuperGroupDataMapper {

  Stream<SuperGroup> findSuperGroups(@NotNull GitAccount gitAccount);
}
