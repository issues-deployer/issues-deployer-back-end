package com.vaddemgen.issuesdeployer.test.util;

import static java.util.Collections.emptyList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class ProjectTestUtils {

  private static final Random random = new Random();

  /**
   * Don't let anyone to instantiate this class.
   */
  private ProjectTestUtils() {
  }

  public static Project createProject(@NotNull Group group) {
    long id = random.nextLong();
    try {
      return Project.builder()
          .id(id)
          .group(group)
          .code("project_code_" + id)
          .path("project_path_" + id)
          .name("Project Name " + id)
          .description("Project Description " + id)
          .createdAt(ZonedDateTime.now())
          .lastActivityAt(ZonedDateTime.now())
          .webUrl(new URL("https://" + id + ".test/com"))
          .issues(emptyList())
          .build();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
