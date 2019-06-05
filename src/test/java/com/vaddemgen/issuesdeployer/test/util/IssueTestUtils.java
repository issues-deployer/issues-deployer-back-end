package com.vaddemgen.issuesdeployer.test.util;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class IssueTestUtils {

  private static final Random random = new Random();

  /**
   * Don't let anyone to instantiate this class.
   */
  private IssueTestUtils() {
  }

  public static Issue createIssue(@NotNull Project project) {
    long id = random.nextLong();
    try {
      return Issue.builder()
          .id(id)
          .project(project)
          .title("issue_title" + id)
          .description("Project Description " + id)
          .labels(new String[]{"label1", "label2"})
          .webUrl(new URL("https://" + id + ".test/com"))
          .build();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
