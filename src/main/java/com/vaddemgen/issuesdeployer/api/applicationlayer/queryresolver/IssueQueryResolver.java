package com.vaddemgen.issuesdeployer.api.applicationlayer.queryresolver;

import static java.util.stream.Collectors.toList;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.vaddemgen.issuesdeployer.api.applicationlayer.model.presenter.IssueDataBean;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.service.IssueService;
import java.util.Collections;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public final class IssueQueryResolver implements GraphQLQueryResolver {

  private final IssueService issueService;

  public IssueQueryResolver(@NonNull IssueService issueService) {
    this.issueService = issueService;
  }

  public List<IssueDataBean> findAllIssuesBy(long projectId) {
    User user = getSessionUser();
    return issueService.getIssuesBy(user, projectId)
        .map(IssueDataBean::of)
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
