package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class SuperGroupDataMapperImpl implements SuperGroupDataMapper {

  @NotNull
  @Override
  public Stream<SuperGroup> findSuperGroups(@NotNull GitAccount gitAccount) {
    return Stream.empty();
  }
}
