package com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job;

import static java.util.Objects.requireNonNull;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.service.SuperGroupService;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClientFactory;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SyncSuperGroupsJob extends AbstractSyncGroupsJob {

  public static final String JOB_SYNC_SUPER_GROUPS = "JOB_SYNC_SUPER_GROUPS";

  private static final Logger LOGGER = LoggerFactory.getLogger(SyncSuperGroupsJob.class);

  private final GitClientFactory gitClientFactory;
  private final SuperGroupService superGroupService;
  private final JobDetail subGroupsJobDetail;

  public SyncSuperGroupsJob(
      @NonNull GitClientFactory gitClientFactory,
      @NonNull SuperGroupService superGroupService,
      @NonNull @Qualifier("syncSubGroupsJobBean") JobDetail subGroupsJobDetail,
      @NonNull @Qualifier("syncProjectsJobBean") JobDetail projectsJobDetail
  ) {
    super(projectsJobDetail);
    this.gitClientFactory = gitClientFactory;
    this.superGroupService = superGroupService;
    this.subGroupsJobDetail = subGroupsJobDetail;
  }

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    LOGGER.trace("{}: Started", JOB_SYNC_SUPER_GROUPS);

    var gitAccount = (GitAccount) requireNonNull(context.getMergedJobDataMap().get("gitAccount"));

    LOGGER.trace("{}: Git account: {}", JOB_SYNC_SUPER_GROUPS, gitAccount);

    var gitClient = gitClientFactory.createGitClient(gitAccount);
    Scheduler scheduler = context.getScheduler();

    try {
      superGroupService.mergeSuperGroups(
          gitAccount,
          gitClient.findSuperGroups().collect(Collectors.toList())
      ).forEach(superGroup -> {
        fireSyncSubGroupsJob(gitAccount, superGroup, scheduler);
        fireSyncProjectsJob(gitAccount, superGroup, scheduler);
      });
    } catch (RuntimeException | IOException | InterruptedException e) {
      String message = String.format(
          "%s: Something went wrong for git account: %s",
          JOB_SYNC_SUPER_GROUPS,
          gitAccount
      );
      LOGGER.error(message, e);
      throw new JobExecutionException(e);
    }

    LOGGER.trace("{}: Finished", JOB_SYNC_SUPER_GROUPS);
  }

  private void fireSyncSubGroupsJob(
      GitAccount gitAccount,
      SuperGroup superGroup,
      Scheduler scheduler
  ) {
    try {
      scheduler.triggerJob(
          subGroupsJobDetail.getKey(),
          new JobDataMap(Map.of("gitAccount", gitAccount, "superGroup", superGroup))
      );
    } catch (SchedulerException e) {
      LOGGER.error(String.format(
          "Cannot fire the job '%s': Something went wrong",
          SyncSubGroupsJob.JOB_SYNC_SUB_GROUPS
      ), e);
      throw new RuntimeException(e);
    }
  }

}
