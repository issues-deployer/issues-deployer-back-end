package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.util.List;
import java.util.stream.Stream;

public interface SubGroupDataMapper {

  Stream<SubGroup> findSubGroupsBySuperGroup(GitAccount gitAccount, SuperGroup superGroup);

  Stream<SubGroup> saveSubGroups(SuperGroup superGroup, List<SubGroup> subGroups);

  Stream<SubGroup> mergeSubGroups(SuperGroup superGroup, List<SubGroup> subGroups);
}
