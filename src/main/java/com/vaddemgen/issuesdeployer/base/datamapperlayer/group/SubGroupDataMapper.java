package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.util.List;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

public interface SubGroupDataMapper {

  Stream<SubGroup> findSubGroupsBySuperGroup(
      @NotNull GitAccount gitAccount,
      @NotNull SuperGroup superGroup
  );

  @Transactional
  void saveSubGroups(
      @NotNull SuperGroup superGroup,
      @NotNull List<SubGroup> subGroups
  );
}
