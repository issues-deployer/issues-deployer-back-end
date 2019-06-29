package com.vaddemgen.issuesdeployer.api.applicationlayer.queryresolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.vaddemgen.issuesdeployer.api.applicationlayer.model.presenter.SubGroupDataBean;
import com.vaddemgen.issuesdeployer.api.applicationlayer.model.presenter.SuperGroupDataBean;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.service.SubGroupService;
import com.vaddemgen.issuesdeployer.base.businesslayer.service.SuperGroupService;
import graphql.schema.DataFetchingEnvironment;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public final class GroupQueryResolver implements GraphQLQueryResolver {

  private final SuperGroupService superGroupService;
  private final SubGroupService subGroupService;

  public GroupQueryResolver(
      @NonNull SuperGroupService superGroupService,
      @NonNull SubGroupService subGroupService
  ) {
    this.superGroupService = superGroupService;
    this.subGroupService = subGroupService;
  }

  // Used by GraphQL.
  @SuppressWarnings("unused")
  public List<SuperGroupDataBean> findAllSuperGroups() {
    User user = getSessionUser();
    return superGroupService.getAllBy(user)
        .map(SuperGroupDataBean::of)
        .collect(Collectors.toList());
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

  public List<SubGroupDataBean> findAllSubGroupsBy(long superGroupId,
      DataFetchingEnvironment environment) {
    User user = getSessionUser();
    return subGroupService.getAllBy(user, superGroupId)
        .map(SubGroupDataBean::of)
        .collect(Collectors.toList());
  }
}
