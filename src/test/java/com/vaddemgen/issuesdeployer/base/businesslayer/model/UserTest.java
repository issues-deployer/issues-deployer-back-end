package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import static com.vaddemgen.issuesdeployer.test.util.GitAccountTestUtils.createGitAccount;
import static com.vaddemgen.issuesdeployer.test.util.UserTestUtils.createTestUser;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the domain model {@link User}.
 */
class UserTest {

  /**
   * Test the method {@link User#clonePartially()}.
   */
  @Test
  void testClonePartially() {
    // Given
    User givenUser = createTestUser();

    // When
    User actualUser = givenUser.clonePartially().build();

    // Then
    assertNotSame(givenUser, actualUser);
    assertEquals(givenUser, actualUser);
    assertEquals(givenUser.getGitAccounts().collect(toList()),
        actualUser.getGitAccounts().collect(toList()));

    // Given
    long givenId = 1L;
    String givenFirstName = "Clone Partially First Name";
    String givenLastName = "Clone Partially Last Name";
    String givenUsername = "clone_partially_username";
    List<GitAccount> givenGitAccounts = singletonList(createGitAccount(givenUser));

    // When
    actualUser = givenUser.clonePartially()
        .id(givenId)
        .firstName(givenFirstName)
        .lastName(givenLastName)
        .username(givenUsername)
        .gitAccounts(givenGitAccounts)
        .build();

    // Then
    assertEquals(givenId, actualUser.getId());
    assertEquals(givenFirstName, actualUser.getFirstName());
    assertEquals(givenLastName, actualUser.getLastName());
    assertEquals(givenUsername, actualUser.getUsername());
    assertEquals(givenGitAccounts, actualUser.getGitAccounts().collect(toList()));
  }

  /**
   * Test the method {@link User#equals(Object)}.
   */
  @Test
  void testEquals() {
    // Given
    User givenUser = createTestUser();

    // Then
    // The equals contract: Reflexivity.
    //noinspection EqualsWithItself,SimplifiableJUnitAssertion
    assertTrue(givenUser.equals(givenUser));
    // The equals contract: Non-nullity.
    //noinspection ConstantConditions,SimplifiableJUnitAssertion
    assertFalse(givenUser.equals(null));
    // noinspection SimplifiableJUnitAssertion
    assertFalse(givenUser.equals(new Object()));

    // When
    User actualUser = givenUser.clonePartially()
        .gitAccounts(singletonList(createGitAccount(givenUser)))
        .build();

    // Then
    // The equals contract: Symmetry.
    assertEquals(givenUser, actualUser);
    assertEquals(actualUser, givenUser);
    assertNotEquals(givenUser.getGitAccounts().collect(toList()),
        actualUser.getGitAccounts().collect(toList()));

    // The equals contract: Transitivity.
    User actualUser2 = givenUser.clonePartially().build();
    assertEquals(givenUser, actualUser);
    assertEquals(givenUser, actualUser2);
    assertEquals(actualUser, actualUser2);
  }

  /**
   * Test the method {@link User#hashCode()}.
   */
  @Test
  void testHashCode() {
    // Given
    User givenUser = createTestUser();

    // When
    User actualUser = givenUser.clonePartially().build();

    // Then
    assertEquals(givenUser, actualUser);
    assertEquals(givenUser.hashCode(), actualUser.hashCode());

    // When
    actualUser = givenUser.clonePartially().username("test_hash_code_username").build();

    // Then
    assertNotEquals(givenUser, actualUser);
    assertNotEquals(givenUser.hashCode(), actualUser.hashCode());

    // When
    actualUser = givenUser.clonePartially().lastName("Test Hash Code First Name").build();

    // Then
    assertNotEquals(givenUser, actualUser);
    assertEquals(givenUser.hashCode(), actualUser.hashCode());
  }
}