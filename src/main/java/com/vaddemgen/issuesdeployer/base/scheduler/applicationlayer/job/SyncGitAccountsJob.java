package com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job;

import static com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job.SyncSuperGroupsJob.JOB_SYNC_SUPER_GROUPS;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.service.GitAccountService;
import java.util.Map;
import lombok.NonNull;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class SyncGitAccountsJob extends QuartzJobBean {

  public static final String JOB_SYNC_GIT_ACCOUNTS = "JOB_SYNC_GIT_ACCOUNTS";

  private static final Logger LOGGER = LoggerFactory.getLogger(SyncGitAccountsJob.class);

  private final GitAccountService service;
  private final JobDetail jobDetail;

  public SyncGitAccountsJob(
      @NonNull GitAccountService service,
      @NonNull @Qualifier("syncSuperGroupsJobBean") JobDetail jobDetail
  ) {
    this.jobDetail = jobDetail;
    this.service = service;
  }

  @Override
  protected void executeInternal(JobExecutionContext context) {
    LOGGER.trace("{}: Started", JOB_SYNC_GIT_ACCOUNTS);

    Scheduler scheduler = context.getScheduler();

    service.getAllGitAccounts()
        .forEach(gitAccount -> fireSyncGroupJob(gitAccount, scheduler));

    LOGGER.trace("{}: Finished", JOB_SYNC_GIT_ACCOUNTS);
  }

  private void fireSyncGroupJob(GitAccount gitAccount, Scheduler scheduler) {
    try {
      scheduler.triggerJob(
          jobDetail.getKey(),
          new JobDataMap(Map.of("gitAccount", gitAccount))
      );
    } catch (SchedulerException e) {
      LOGGER.error(String.format(
          "Cannot fire the job '%s': Something went wrong",
          JOB_SYNC_SUPER_GROUPS
      ), e);
      throw new RuntimeException(e);
    }
  }
}
