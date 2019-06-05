package com.vaddemgen.issuesdeployer.test.util;

import static java.lang.Math.abs;
import static java.util.Collections.emptyList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import java.util.Random;

public final class UserTestUtils {

  private static final Random random = new Random();

  /**
   * Don't let anyone to instantiate this class.
   */
  private UserTestUtils() {
  }

  public static User createTestUser() {
    long id = abs(random.nextLong());
    return User.builder()
        .id(id)
        .firstName("John")
        .lastName("Smith")
        .username("test_" + id)
        .gitAccounts(emptyList())
        .build();
  }
}
