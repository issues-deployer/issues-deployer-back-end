package com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job;

import static java.util.Objects.requireNonNull;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import com.vaddemgen.issuesdeployer.base.businesslayer.service.ProjectService;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClientFactory;
import java.io.IOException;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SyncProjectsJob extends AbstractSyncProjectsJob {

  public static final String JOB_SYNC_PROJECTS = "JOB_SYNC_PROJECTS";

  private static final Logger LOGGER = LoggerFactory.getLogger(SyncProjectsJob.class);

  private final GitClientFactory gitClientFactory;
  private final ProjectService projectService;

  public SyncProjectsJob(
      @NonNull GitClientFactory gitClientFactory,
      @NonNull ProjectService projectService,
      @NonNull @Qualifier("syncIssuesJobBean") JobDetail syncProjectsJobDetail
  ) {
    super(syncProjectsJobDetail);
    this.gitClientFactory = gitClientFactory;
    this.projectService = projectService;
  }

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    LOGGER.trace("{}: Started", JOB_SYNC_PROJECTS);

    var gitAccount = (GitAccount) requireNonNull(context.getMergedJobDataMap().get("gitAccount"));
    var group = (Group) requireNonNull(context.getMergedJobDataMap().get("group"));

    LOGGER.trace(
        "{}: Git account: {}, Group: {}",
        JOB_SYNC_PROJECTS,
        gitAccount,
        group
    );

    var gitClient = gitClientFactory.createGitClient(gitAccount);
    Scheduler scheduler = context.getScheduler();

    try {
      projectService.mergeProjects(
          group,
          gitClient.findProjects(group).collect(Collectors.toList())
      ).forEach(project -> fireSyncIssuesJob(gitAccount, project, scheduler));
    } catch (RuntimeException | IOException | InterruptedException e) {
      String message = String.format(
          "%s: Something went wrong for git account: %s, group: %s",
          JOB_SYNC_PROJECTS,
          gitAccount,
          group
      );
      LOGGER.error(message, e);
      throw new JobExecutionException(e);
    }

    LOGGER.trace("{}: Finished", JOB_SYNC_PROJECTS);
  }
}
