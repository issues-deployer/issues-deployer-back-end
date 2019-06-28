package com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.service.GitAccountService;
import com.vaddemgen.issuesdeployer.base.businesslayer.service.ProjectService;
import lombok.NonNull;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SyncAllIssuesJob extends AbstractSyncProjectsJob {

  public static final String JOB_ALL_SYNC_ISSUES = "JOB_ALL_SYNC_ISSUES";

  private static final Logger LOGGER = LoggerFactory.getLogger(SyncAllIssuesJob.class);

  private final GitAccountService gitAccountService;
  private final ProjectService projectService;

  public SyncAllIssuesJob(
      @NonNull @Qualifier("syncIssuesJobBean") JobDetail projectsJobDetail,
      @NonNull GitAccountService gitAccountService,
      @NonNull ProjectService projectService
  ) {
    super(projectsJobDetail);
    this.gitAccountService = gitAccountService;
    this.projectService = projectService;
  }

  @Override
  protected void executeInternal(JobExecutionContext context) {
    LOGGER.trace("{}: Started", JOB_ALL_SYNC_ISSUES);

    Scheduler scheduler = context.getScheduler();
    gitAccountService.getAllGitAccounts()
        .forEach(gitAccount -> syncIssuesFor(gitAccount, scheduler));

    LOGGER.trace("{}: Finished", JOB_ALL_SYNC_ISSUES);
  }

  private void syncIssuesFor(GitAccount gitAccount, Scheduler scheduler) {
    LOGGER.trace("{}: Git account: {}", JOB_ALL_SYNC_ISSUES, gitAccount);
    projectService.getProjectsBy(gitAccount)
        .forEach(project -> fireSyncIssuesJob(gitAccount, project, scheduler));
  }
}
