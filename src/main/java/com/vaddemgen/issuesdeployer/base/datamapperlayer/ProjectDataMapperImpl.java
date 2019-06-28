package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import static com.vaddemgen.issuesdeployer.base.datamapperlayer.factory.ProjectEntityFactory.createProjectEntity;
import static com.vaddemgen.issuesdeployer.base.datamapperlayer.factory.ProjectFactory.createProject;
import static com.vaddemgen.issuesdeployer.base.datamapperlayer.group.util.ProjectEntityUtil.mergeProjectEntity;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.factory.ProjectFactory;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository.GroupRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.GroupEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository.ProjectRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.ProjectEntity;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClientFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProjectDataMapperImpl implements ProjectDataMapper {

  private final GitClientFactory gitClientFactory;
  private final ProjectRepository projectRepository;
  private final GroupRepository<GroupEntity> groupRepository;

  // Self autowired
  private ProjectDataMapperImpl self;

  public ProjectDataMapperImpl(
      @NotNull GitClientFactory gitClientFactory,
      @NotNull ProjectRepository projectRepository,
      @NotNull GroupRepository<GroupEntity> groupRepository
  ) {
    this.gitClientFactory = gitClientFactory;
    this.projectRepository = projectRepository;
    this.groupRepository = groupRepository;
  }

  @Autowired
  public void setSelf(@NotNull ProjectDataMapperImpl self) {
    this.self = self;
  }

  @Override
  public Stream<Project> findProjectsByGroup(
      @NotNull GitAccount gitAccount,
      @NotNull Group group
  ) {

    var projects = projectRepository.findAllByGroupId(group.getId());

    if (!projects.isEmpty()) {
      return projects.stream()
          .map(ProjectFactory::createProject);
    }

    var gitClient = gitClientFactory.createGitClient(gitAccount);
    try {
      List<Project> loadedProjects = gitClient.findProjects(group).collect(toList());
      if (!loadedProjects.isEmpty()) {
        return self.saveProjects(group, loadedProjects);
      }
    } catch (IOException | InterruptedException e) { // TODO: Handle the exceptions
      e.printStackTrace();
    }
    return Stream.empty();
  }

  @Override
  @Transactional
  public Stream<Project> saveProjects(
      @NonNull Group projectsOwner,
      @NonNull Collection<Project> projects
  ) {
    return createProjects(
        projects,
        groupRepository.findOneForShare(projectsOwner.getId())
            .orElseThrow() // TODO: handle the exception
    );
  }

  @Transactional
  @Override
  public Stream<Project> mergeProjects(
      @NonNull Group projectsOwner,
      @NonNull List<Project> projectsForMerge
  ) {
    GroupEntity projectsOwnerEntity = groupRepository.findOneForShare(projectsOwner.getId())
        .orElseThrow(); // TODO: handle the exception

    var existedEntities = projectRepository.findAllForUpdateBy(projectsOwnerEntity.getId());

    // Splitting the project entities for deletion (TRUE) and update (FALSE).
    var map = splitProjectEntitiesForDeleteAndMerge(
        existedEntities,
        projectsForMerge.stream().mapToLong(Project::getRemoteId).toArray()
    );

    // Deleting the entities.
    deleteProjectEntities(map.get(true));

    // Save the entities.
    return saveProjectEntities(projectsForMerge, map.get(false), projectsOwnerEntity);
  }

  @Override
  public Stream<Project> findProjectsBy(GitAccount gitAccount) {
    return projectRepository.findAllByGitAccountId(gitAccount.getId())
        .stream()
        .map(ProjectFactory::createProject);
  }

  /**
   * Splits the project entities for deletion (TRUE) and update (FALSE).
   */
  private Map<Boolean, List<ProjectEntity>> splitProjectEntitiesForDeleteAndMerge(
      @NonNull List<ProjectEntity> existedEntities,
      @NonNull long[] remoteIds
  ) {
    return existedEntities.stream()
        .collect(partitioningBy(
            projectEntity -> LongStream.of(remoteIds)
                .noneMatch(remoteId -> remoteId == projectEntity.getRemoteId())
        ));
  }

  private void deleteProjectEntities(List<ProjectEntity> entitiesForDeletion) {
    projectRepository.deleteAll(entitiesForDeletion);
  }

  private Stream<Project> saveProjectEntities(
      @NonNull List<Project> modelsForSave,
      @NonNull List<ProjectEntity> existedEntities,
      @NonNull GroupEntity projectsOwnerEntity
  ) {
    List<Project> mergedSuperGroups = new ArrayList<>(modelsForSave.size());

    var map = splitProjectsForCreateAndUpdate(
        modelsForSave,
        existedEntities.stream().mapToLong(ProjectEntity::getRemoteId).toArray()
    );

    // Creating entities.
    mergedSuperGroups.addAll(
        createProjects(map.get(true), projectsOwnerEntity).collect(toList())
    );

    // Updating entities.
    mergedSuperGroups.addAll(
        updateSubGroups(existedEntities, map.get(false)).collect(toList())
    );

    return mergedSuperGroups.stream();
  }

  /**
   * Splits the project models for create (TRUE) and update (FALSE).
   */
  private Map<Boolean, List<Project>> splitProjectsForCreateAndUpdate(
      @NonNull List<Project> modelsForSave,
      @NonNull long[] existedRemoteIds
  ) {
    return modelsForSave.stream()
        .collect(partitioningBy(
            project -> LongStream.of(existedRemoteIds)
                .noneMatch(remoteId -> remoteId == project.getRemoteId())
        ));
  }

  private Stream<Project> createProjects(
      @NonNull Collection<Project> projects,
      @NonNull GroupEntity projectsOwnerEntity
  ) {
    return projectRepository
        .saveAll(
            projects.stream()
                .map(project -> createProjectEntity(project, projectsOwnerEntity))
                .collect(toList())
        )
        .stream()
        .map(ProjectFactory::createProject);
  }

  private Stream<Project> updateSubGroups(
      @NonNull List<ProjectEntity> existedEntities,
      @NonNull List<Project> projectsForUpdate
  ) {
    return mapProjects(projectsForUpdate, existedEntities)
        .entrySet()
        .stream()
        .map((entry) -> {
          var projectEntity = entry.getValue().orElseThrow();
          mergeProjectEntity(entry.getKey(), projectEntity);
          return createProject(projectRepository.save(projectEntity));
        });
  }

  /**
   * <p>Maps projects and the related project entities by {@link Project#getRemoteId()}.</p>
   */
  private Map<Project, Optional<ProjectEntity>> mapProjects(
      @NonNull List<Project> projects,
      @NonNull List<ProjectEntity> projectEntities
  ) {
    return projects.stream()
        .collect(toMap(
            project -> project,
            project ->
                projectEntities.stream()
                    .filter(entity -> project.getRemoteId() == entity.getRemoteId())
                    .findAny()
        ));
  }
}
