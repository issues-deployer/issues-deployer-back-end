package com.vaddemgen.issuesdeployer.test.util;

import static java.util.Collections.emptyList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class SubGroupTestUtils {

  private static final Random random = new Random();

  /**
   * Don't let anyone to instantiate this class.
   */
  private SubGroupTestUtils() {
  }

  public static SubGroup createSubGroup(@NotNull SuperGroup superGroup) {
    long id = random.nextLong();
    try {
      return SubGroup.builder()
          .id(id)
          .superGroup(superGroup)
          .code("sup_group_code_" + id)
          .path("sup_group_path_" + id)
          .shortName("Sup Group Short Name " + id)
          .name("Sup Group Name " + id)
          .webUrl(new URL("https://" + id + ".test/com"))
          .description("Sup Group Description " + id)
          .projects(emptyList())
          .build();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
