package com.vaddemgen.issuesdeployer.base.applicationlayer.controller;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.service.SuperGroupService;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Stream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  private final SuperGroupService superGroupService;

  public TestController(SuperGroupService superGroupService) {
    this.superGroupService = superGroupService;
  }

  @RequestMapping("test")
  public Stream<SuperGroup> findIssues() throws IOException, InterruptedException {
    return superGroupService.findSuperGroupsTree(
        User.builder()
            .id(1L)
            .firstName("Agent")
            .lastName("Smith 1")
            .username("agent_smith_1")
            .gitAccounts(Collections.emptyList())
            .build()
    );
  }
}
