package com.vaddemgen.issuesdeployer.base.datamapperlayer;

import static com.vaddemgen.issuesdeployer.base.datamapperlayer.factory.IssueEntityFactory.createIssueEntity;
import static java.util.stream.Collectors.toList;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.factory.IssueFactory;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository.IssueRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.jparepository.ProjectRepository;
import com.vaddemgen.issuesdeployer.base.datamapperlayer.orm.ProjectEntity;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClientFactory;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class IssueDataMapperImpl implements IssueDataMapper {

  private final GitClientFactory gitClientFactory;
  private final IssueRepository issueRepository;
  private final ProjectRepository projectRepository;

  // Self autowired
  private IssueDataMapperImpl self;

  public IssueDataMapperImpl(
      GitClientFactory gitClientFactory,
      IssueRepository issueRepository,
      ProjectRepository projectRepository
  ) {
    this.gitClientFactory = gitClientFactory;
    this.issueRepository = issueRepository;
    this.projectRepository = projectRepository;
  }

  @Autowired
  public void setSelf(IssueDataMapperImpl self) {
    this.self = self;
  }

  @Override
  public Stream<Issue> findIssuesByProject(GitAccount gitAccount, Project project) {

    var issues = issueRepository
        .findAllByProjectId(project.getId());

    if (!issues.isEmpty()) {
      return issues.stream()
          .map(IssueFactory::createIssue);
    }

    var client = gitClientFactory.createGitClient(gitAccount);

    try {
      List<Issue> loadedIssues = client.findIssues(project).collect(toList());
      if (!loadedIssues.isEmpty()) {
        return self.saveIssues(project, loadedIssues);
      }
    } catch (IOException | InterruptedException e) { // TODO: handle the exception
      e.printStackTrace();
    }

    return Stream.empty();
  }

  @Override
  @Transactional
  public Stream<Issue> saveIssues(Project project, Collection<Issue> issues) {
    ProjectEntity projectEntity = projectRepository.findOneForShare(project.getId())
        .orElseThrow(); // TODO: Refactor to ModelNotFoundException

    return issueRepository
        .saveAll(
            issues.stream()
                .map(issue -> createIssueEntity(issue, projectEntity))
                .collect(toList())
        )
        .stream()
        .map(IssueFactory::createIssue);
  }
}
