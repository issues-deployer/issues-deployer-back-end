package com.vaddemgen.issuesdeployer.base.businesslayer.service;

import static com.vaddemgen.issuesdeployer.test.util.GitAccountTestUtils.createGitAccount;
import static com.vaddemgen.issuesdeployer.test.util.IssueTestUtils.createIssue;
import static com.vaddemgen.issuesdeployer.test.util.ProjectTestUtils.createProject;
import static com.vaddemgen.issuesdeployer.test.util.SubGroupTestUtils.createSubGroup;
import static com.vaddemgen.issuesdeployer.test.util.SuperGroupTestUtils.createSuperGroup;
import static com.vaddemgen.issuesdeployer.test.util.UserTestUtils.createTestUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.GitAccountDataMapper;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.IssueDataMapper;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.ProjectDataMapper;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.SubGroupDataMapper;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.SuperGroupDataMapper;
import com.vaddemgen.issuesdeployer.test.category.Fast;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the class {@link SuperGroupServiceImpl}.
 */
@Fast
class SuperGroupServiceImplTest {

  /**
   * Tests the method {@link SuperGroupServiceImpl#findSuperGroupsTree(User)} when a user does'n
   * have git accounts.
   */
  @Test
  void testFindSuperGroupsTreeForEmptyUserGitAccounts() {
    // Given
    GitAccountDataMapper mockedGitAccountDataMapper = mock(GitAccountDataMapper.class);
    SuperGroupDataMapper mockedSuperGroupDataMapper = mock(SuperGroupDataMapper.class);
    SubGroupDataMapper mockedSubGroupDataMapper = mock(SubGroupDataMapper.class);
    ProjectDataMapper mockedProjectDataMapper = mock(ProjectDataMapper.class);
    IssueDataMapper mockedIssueDataMapper = mock(IssueDataMapper.class);

    User givenUser = createTestUser();

    // When
    SuperGroupService service = new SuperGroupServiceImpl(mockedGitAccountDataMapper,
        mockedSuperGroupDataMapper, mockedSubGroupDataMapper, mockedProjectDataMapper,
        mockedIssueDataMapper);

    Stream<SuperGroup> actual = service.findSuperGroupsTree(givenUser);

    // Then
    assertEquals(0, actual.count());
  }

  /**
   * Tests the method {@link SuperGroupServiceImpl#findSuperGroupsTree(User)} when a git account
   * doesn't have super groups.
   */
  @Test
  void testFindSuperGroupsTreeForEmptySuperGroups() {
    // Given
    GitAccountDataMapper mockedGitAccountDataMapper = mock(GitAccountDataMapper.class);
    SuperGroupDataMapper mockedSuperGroupDataMapper = mock(SuperGroupDataMapper.class);
    SubGroupDataMapper mockedSubGroupDataMapper = mock(SubGroupDataMapper.class);
    ProjectDataMapper mockedProjectDataMapper = mock(ProjectDataMapper.class);
    IssueDataMapper mockedIssueDataMapper = mock(IssueDataMapper.class);

    User givenUser = createTestUser();

    createGitAccountAndMock(givenUser, mockedGitAccountDataMapper);

    // When
    SuperGroupService service = new SuperGroupServiceImpl(mockedGitAccountDataMapper,
        mockedSuperGroupDataMapper, mockedSubGroupDataMapper, mockedProjectDataMapper,
        mockedIssueDataMapper);

    Stream<SuperGroup> actual = service.findSuperGroupsTree(givenUser);

    // Then
    assertEquals(0, actual.count());
  }

  /**
   * Tests the method {@link SuperGroupServiceImpl#findSuperGroupsTree(User)} when a super group
   * doesn't have projects and sup groups.
   */
  @Test
  void testFindSuperGroupsTreeForEmptySuperGroupsProjectsAndSubGroups() {
    // Given
    GitAccountDataMapper mockedGitAccountDataMapper = mock(GitAccountDataMapper.class);
    SuperGroupDataMapper mockedSuperGroupDataMapper = mock(SuperGroupDataMapper.class);
    SubGroupDataMapper mockedSubGroupDataMapper = mock(SubGroupDataMapper.class);
    ProjectDataMapper mockedProjectDataMapper = mock(ProjectDataMapper.class);
    IssueDataMapper mockedIssueDataMapper = mock(IssueDataMapper.class);

    User givenUser = createTestUser();

    GitAccount givenGitAccount = createGitAccountAndMock(givenUser, mockedGitAccountDataMapper);

    var givenSuperGroup = createSuperGroupAndMock(givenGitAccount, mockedSuperGroupDataMapper);

    // When
    SuperGroupService service = new SuperGroupServiceImpl(mockedGitAccountDataMapper,
        mockedSuperGroupDataMapper, mockedSubGroupDataMapper, mockedProjectDataMapper,
        mockedIssueDataMapper);

    List<SuperGroup> actual = service.findSuperGroupsTree(givenUser)
        .collect(Collectors.toList());

    // Then
    assertEquals(1, actual.size());

    SuperGroup actualSuperGroup = actual.get(0);

    assertEquals(givenSuperGroup, actualSuperGroup);

    assertEquals(0, actualSuperGroup.getSubGroups().count());
    assertEquals(0, actualSuperGroup.getProjects().count());
  }

  /**
   * Tests the method {@link SuperGroupServiceImpl#findSuperGroupsTree(User)} when a sup group
   * doesn't have projects.
   */
  @Test
  void testFindSuperGroupsTreeForEmptySupGroupsProjects() {
    // Given
    GitAccountDataMapper mockedGitAccountDataMapper = mock(GitAccountDataMapper.class);
    SuperGroupDataMapper mockedSuperGroupDataMapper = mock(SuperGroupDataMapper.class);
    SubGroupDataMapper mockedSubGroupDataMapper = mock(SubGroupDataMapper.class);
    ProjectDataMapper mockedProjectDataMapper = mock(ProjectDataMapper.class);
    IssueDataMapper mockedIssueDataMapper = mock(IssueDataMapper.class);

    User givenUser = createTestUser();

    GitAccount givenGitAccount = createGitAccountAndMock(givenUser, mockedGitAccountDataMapper);

    var givenSuperGroup = createSuperGroupAndMock(givenGitAccount, mockedSuperGroupDataMapper);

    SubGroup givenSubGroup = createSubGroupAndMock(givenSuperGroup, givenGitAccount,
        mockedSubGroupDataMapper);

    Project givenProject = createProjectAndMock(givenSuperGroup, mockedProjectDataMapper);

    // When
    SuperGroupService service = new SuperGroupServiceImpl(mockedGitAccountDataMapper,
        mockedSuperGroupDataMapper, mockedSubGroupDataMapper, mockedProjectDataMapper,
        mockedIssueDataMapper);

    List<SuperGroup> actual = service.findSuperGroupsTree(givenUser)
        .collect(Collectors.toList());

    // Then
    assertEquals(1, actual.size());

    SuperGroup actualSuperGroup = actual.get(0);

    assertEquals(givenSuperGroup, actualSuperGroup);

    assertEquals(1, actualSuperGroup.getSubGroups().count());

    Optional<SubGroup> actualSubGroup = actualSuperGroup.getSubGroups().findAny();

    assertTrue(actualSubGroup.isPresent());

    assertEquals(givenSubGroup, actualSubGroup.get());

    assertEquals(givenSubGroup, actualSubGroup.get());

    assertEquals(0, actualSubGroup.get().getProjects().count());

    assertEquals(1, actualSuperGroup.getProjects().count());

    Optional<Project> actualProject = actualSuperGroup.getProjects().findAny();

    assertTrue(actualProject.isPresent());

    assertEquals(givenProject, actualProject.get());

    assertEquals(0, actualProject.get().getIssues().count());
  }

  /**
   * Tests the method {@link SuperGroupServiceImpl#findSuperGroupsTree(User)} when groups don't have
   * projects.
   */
  @Test
  void testFindSuperGroupsTreeForEmptyIssues() {
    // Given
    GitAccountDataMapper mockedGitAccountDataMapper = mock(GitAccountDataMapper.class);
    SuperGroupDataMapper mockedSuperGroupDataMapper = mock(SuperGroupDataMapper.class);
    SubGroupDataMapper mockedSubGroupDataMapper = mock(SubGroupDataMapper.class);
    ProjectDataMapper mockedProjectDataMapper = mock(ProjectDataMapper.class);
    IssueDataMapper mockedIssueDataMapper = mock(IssueDataMapper.class);

    User givenUser = createTestUser();

    GitAccount givenGitAccount = createGitAccountAndMock(givenUser, mockedGitAccountDataMapper);

    var givenSuperGroup = createSuperGroupAndMock(givenGitAccount, mockedSuperGroupDataMapper);

    SubGroup givenSubGroup = createSubGroupAndMock(givenSuperGroup, givenGitAccount,
        mockedSubGroupDataMapper);

    Project givenProject1 = createProjectAndMock(givenSubGroup, mockedProjectDataMapper);

    Project givenProject2 = createProjectAndMock(givenSuperGroup, mockedProjectDataMapper);

    // When
    SuperGroupService service = new SuperGroupServiceImpl(mockedGitAccountDataMapper,
        mockedSuperGroupDataMapper, mockedSubGroupDataMapper, mockedProjectDataMapper,
        mockedIssueDataMapper);

    List<SuperGroup> actual = service.findSuperGroupsTree(givenUser)
        .collect(Collectors.toList());

    // Then
    assertEquals(1, actual.size());

    SuperGroup actualSuperGroup = actual.get(0);

    assertEquals(givenSuperGroup, actualSuperGroup);

    assertEquals(1, actualSuperGroup.getSubGroups().count());

    Optional<SubGroup> actualSubGroup = actualSuperGroup.getSubGroups().findAny();

    assertTrue(actualSubGroup.isPresent());

    assertEquals(givenSubGroup, actualSubGroup.get());

    assertEquals(1, actualSubGroup.get().getProjects().count());

    Optional<Project> actualProject1 = actualSubGroup.get().getProjects().findAny();

    assertTrue(actualProject1.isPresent());

    assertEquals(givenProject1, actualProject1.get());

    assertEquals(0, actualProject1.get().getIssues().count());

    assertEquals(1, actualSuperGroup.getProjects().count());

    Optional<Project> actualProject2 = actualSuperGroup.getProjects().findAny();

    assertTrue(actualProject2.isPresent());

    assertEquals(givenProject2, actualProject2.get());

    assertEquals(0, actualProject2.get().getIssues().count());
  }

  private Project createProjectAndMock(Group group, ProjectDataMapper mockedProjectDataMapper) {
    Project givenProject = createProject(group);
    doReturn(Stream.of(givenProject))
        .when(mockedProjectDataMapper)
        .findProjectsByGroup(eq(group));
    return givenProject;
  }

  /**
   * Tests the method {@link SuperGroupServiceImpl#findSuperGroupsTree(User)}.
   */
  @Test
  void testFindSuperGroups() {
    // Given
    GitAccountDataMapper mockedGitAccountDataMapper = mock(GitAccountDataMapper.class);
    SuperGroupDataMapper mockedSuperGroupDataMapper = mock(SuperGroupDataMapper.class);
    SubGroupDataMapper mockedSubGroupDataMapper = mock(SubGroupDataMapper.class);
    ProjectDataMapper mockedProjectDataMapper = mock(ProjectDataMapper.class);
    IssueDataMapper mockedIssueDataMapper = mock(IssueDataMapper.class);

    User givenUser = createTestUser();

    GitAccount givenGitAccount = createGitAccountAndMock(givenUser, mockedGitAccountDataMapper);

    var givenSuperGroup = createSuperGroupAndMock(givenGitAccount, mockedSuperGroupDataMapper);

    SubGroup givenSubGroup = createSubGroupAndMock(givenSuperGroup, givenGitAccount,
        mockedSubGroupDataMapper);

    Project givenProject1 = createProjectAndMock(givenSubGroup, mockedProjectDataMapper);

    Issue givenIssue1 = createIssueAndMock(givenProject1, mockedIssueDataMapper);

    Project givenProject2 = createProjectAndMock(givenSuperGroup, mockedProjectDataMapper);

    Issue givenIssue2 = createIssueAndMock(givenProject2, mockedIssueDataMapper);

    // When
    SuperGroupService service = new SuperGroupServiceImpl(mockedGitAccountDataMapper,
        mockedSuperGroupDataMapper, mockedSubGroupDataMapper, mockedProjectDataMapper,
        mockedIssueDataMapper);

    List<SuperGroup> actual = service.findSuperGroupsTree(givenUser)
        .collect(Collectors.toList());

    // Then
    assertEquals(1, actual.size());

    SuperGroup actualSuperGroup = actual.get(0);

    assertEquals(givenSuperGroup, actualSuperGroup);

    assertEquals(1, actualSuperGroup.getSubGroups().count());

    Optional<SubGroup> actualSubGroup = actualSuperGroup.getSubGroups().findAny();

    assertTrue(actualSubGroup.isPresent());

    assertEquals(givenSubGroup, actualSubGroup.get());

    assertEquals(1, actualSubGroup.get().getProjects().count());

    Optional<Project> actualProject1 = actualSubGroup.get().getProjects().findAny();

    assertTrue(actualProject1.isPresent());

    assertEquals(givenProject1, actualProject1.get());

    assertEquals(1, actualProject1.get().getIssues().count());

    Optional<Issue> actualIssue1 = actualProject1.get().getIssues().findAny();

    assertTrue(actualIssue1.isPresent());

    assertEquals(givenIssue1, actualIssue1.get());

    assertEquals(1, actualSuperGroup.getProjects().count());

    Optional<Project> actualProject2 = actualSuperGroup.getProjects().findAny();

    assertTrue(actualProject2.isPresent());

    assertEquals(givenProject2, actualProject2.get());

    assertEquals(1, actualProject2.get().getIssues().count());

    Optional<Issue> actualIssue2 = actualProject2.get().getIssues().findAny();

    assertTrue(actualIssue2.isPresent());

    assertEquals(givenIssue2, actualIssue2.get());
  }

  private Issue createIssueAndMock(Project project, IssueDataMapper mockedIssueDataMapper) {
    Issue givenIssue = createIssue(project);
    doReturn(Stream.of(givenIssue))
        .when(mockedIssueDataMapper)
        .findIssuesByProject(eq(project));
    return givenIssue;
  }

  private SubGroup createSubGroupAndMock(SuperGroup givenSuperGroup,
      GitAccount givenGitAccount,
      SubGroupDataMapper mockedSubGroupDataMapper) {
    var givenSubGroup = createSubGroup(givenSuperGroup);
    doReturn(Stream.of(givenSubGroup))
        .when(mockedSubGroupDataMapper)
        .findSubGroupsBySuperGroup(eq(givenGitAccount), eq(givenSuperGroup));
    return givenSubGroup;
  }

  private SuperGroup createSuperGroupAndMock(GitAccount givenGitAccount,
      SuperGroupDataMapper mockedSuperGroupDataMapper) {
    SuperGroup givenSuperGroup = createSuperGroup(givenGitAccount);
    doReturn(Stream.of(givenSuperGroup))
        .when(mockedSuperGroupDataMapper)
        .findSuperGroups(eq(givenGitAccount));
    return givenSuperGroup;
  }

  private GitAccount createGitAccountAndMock(User givenUser,
      GitAccountDataMapper mockedGitAccountDataMapper) {
    GitAccount givenGitAccount = createGitAccount(givenUser);
    doReturn(Stream.of(givenGitAccount))
        .when(mockedGitAccountDataMapper)
        .findGitAccountsByUser(eq(givenUser));
    return givenGitAccount;
  }
}