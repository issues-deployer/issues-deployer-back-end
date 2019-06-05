package com.vaddemgen.issuesdeployer.test.util;

import static java.util.Collections.emptyList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class SuperGroupTestUtils {

  private static final Random random = new Random();

  /**
   * Don't let anyone to instantiate this class.
   */
  private SuperGroupTestUtils() {
  }

  public static SuperGroup createSuperGroup(@NotNull GitAccount gitAccount) {
    long id = random.nextLong();
    try {
      return SuperGroup.builder()
          .id(id)
          .gitAccount(gitAccount)
          .code("super_group_code_" + id)
          .path("super_group_path_" + id)
          .shortName("Super Group Short Name " + id)
          .name("Super Group Name " + id)
          .webUrl(new URL("https://" + id + ".test/com"))
          .description("Super Group Description " + id)
          .projects(emptyList())
          .subGroups(emptyList())
          .build();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
