package com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job;

import static java.util.Objects.requireNonNull;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import com.vaddemgen.issuesdeployer.base.businesslayer.service.SubGroupService;
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
public class SyncSubGroupsJob extends AbstractSyncGroupsJob {

  public static final String JOB_SYNC_SUB_GROUPS = "JOB_SYNC_SUB_GROUPS";

  private static final Logger LOGGER = LoggerFactory.getLogger(SyncSubGroupsJob.class);

  private final GitClientFactory gitClientFactory;
  private final SubGroupService subGroupService;

  public SyncSubGroupsJob(
      @NonNull GitClientFactory gitClientFactory,
      @NonNull SubGroupService subGroupService,
      @NonNull @Qualifier("syncProjectsJobBean") JobDetail projectsJobDetail
  ) {
    super(projectsJobDetail);
    this.gitClientFactory = gitClientFactory;
    this.subGroupService = subGroupService;
  }

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    LOGGER.trace("{}: Started", JOB_SYNC_SUB_GROUPS);

    var gitAccount = (GitAccount) requireNonNull(context.getMergedJobDataMap().get("gitAccount"));
    var superGroup = (SuperGroup) requireNonNull(context.getMergedJobDataMap().get("superGroup"));

    LOGGER.trace(
        "{}: Git account: {}, Super group: {}",
        JOB_SYNC_SUB_GROUPS,
        gitAccount,
        superGroup
    );

    var gitClient = gitClientFactory.createGitClient(gitAccount);
    Scheduler scheduler = context.getScheduler();

    try {
      subGroupService.mergeSubGroups(
          superGroup,
          gitClient.findSubGroups(superGroup).collect(Collectors.toList())
      ).forEach(subGroup -> fireSyncProjectsJob(gitAccount, subGroup, scheduler));
    } catch (RuntimeException | IOException | InterruptedException e) {
      String message = String.format(
          "%s: Something went wrong for git account: %s, super group: %s",
          JOB_SYNC_SUB_GROUPS,
          gitAccount,
          superGroup
      );
      LOGGER.error(message, e);
      throw new JobExecutionException(e);
    }

    LOGGER.trace("{}: Finished", JOB_SYNC_SUB_GROUPS);
  }
}
