package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class SubGroupDataMapperImpl implements SubGroupDataMapper {

  @Override
  public Stream<SubGroup> findSubGroupsBySuperGroup(@NotNull SuperGroup superGroup) {
    return Stream.empty();
  }
}
