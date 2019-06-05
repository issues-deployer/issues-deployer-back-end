package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public final class ProjectDataMapperImpl implements ProjectDataMapper {

  @Override
  public Stream<Project> findProjectsByGroup(@NotNull Group superGroup) {
    return Stream.empty();
  }
}
