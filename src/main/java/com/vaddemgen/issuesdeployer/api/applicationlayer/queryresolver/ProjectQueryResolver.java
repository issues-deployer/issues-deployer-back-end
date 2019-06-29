package com.vaddemgen.issuesdeployer.api.applicationlayer.queryresolver;

import static java.util.stream.Collectors.toList;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.vaddemgen.issuesdeployer.api.applicationlayer.model.presenter.ProjectDataBean;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.service.ProjectService;
import java.util.Collections;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public final class ProjectQueryResolver implements GraphQLQueryResolver {

  private final ProjectService projectService;

  public ProjectQueryResolver(@NonNull ProjectService projectService) {
    this.projectService = projectService;
  }

  public List<ProjectDataBean> findAllProjectsBy(long groupId) {
    User user = getSessionUser();
    return projectService.getProjectsBy(user, groupId)
        .map(ProjectDataBean::of)
        .collect(toList());
  }

  private User getSessionUser() {
    return User.builder()
        .id(1L)
        .firstName("Agent")
        .lastName("Smith")
        .username("agent.smith")
        .gitAccounts(Collections.emptyList())
        .build();
  }
}
