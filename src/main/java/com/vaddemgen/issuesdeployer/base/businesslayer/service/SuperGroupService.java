package com.vaddemgen.issuesdeployer.base.businesslayer.service;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public interface SuperGroupService {

  Stream<SuperGroup> findSuperGroupsTree(@NotNull User user);

  Stream<SuperGroup> mergeSuperGroups(GitAccount gitAccount, List<SuperGroup> superGroups);
}
