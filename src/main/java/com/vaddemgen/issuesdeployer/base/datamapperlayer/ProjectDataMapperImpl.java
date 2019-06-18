package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import static com.vaddemgen.issuesdeployer.base.datamapperlayer.factory.ProjectEntityFactory.createProjectEntity;
import static java.util.stream.Collectors.toList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.factory.ProjectFactory;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.jparepository.GroupRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.group.orm.group.GroupEntity;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository.ProjectRepository;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClientFactory;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
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

    var projects = projectRepository.findAllByGroupId(group.getRemoteId());

    if (!projects.isEmpty()) {
      return projects.stream()
          .map(ProjectFactory::createProject);
    }

    var gitClient = gitClientFactory.createGitClient(gitAccount);
    try {
      List<Project> loadedProjects = gitClient.findProjects(group).collect(toList());

      self.saveProjects(gitAccount, group, loadedProjects);

      return loadedProjects.stream();
    } catch (IOException | InterruptedException e) { // TODO: Handle the exceptions
      e.printStackTrace();
    }
    return Stream.empty();
  }

  @Transactional
  public void saveProjects(GitAccount gitAccount, Group group, Collection<Project> projects) {
    GroupEntity groupEntity = groupRepository.findOneForShare(
        group.getId().orElseThrow(IllegalArgumentException::new)
    ).orElseThrow(IllegalArgumentException::new);

    projectRepository.saveAll(
        projects.stream()
            .map(project -> createProjectEntity(project, groupEntity))
            .collect(toList())
    );
  }
}
