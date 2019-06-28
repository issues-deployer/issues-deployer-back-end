package com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.Group;
import java.util.Map;
import lombok.NonNull;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

abstract class AbstractSyncGroupsJob extends QuartzJobBean {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSyncGroupsJob.class);

  private final JobDetail projectsJobDetail;

  AbstractSyncGroupsJob(@NonNull JobDetail projectsJobDetail) {
    this.projectsJobDetail = projectsJobDetail;
  }

  final void fireSyncProjectsJob(
      @NonNull GitAccount gitAccount,
      @NonNull Group group,
      Scheduler scheduler
  ) {
    try {
      scheduler.triggerJob(
          projectsJobDetail.getKey(),
          new JobDataMap(Map.of("gitAccount", gitAccount, "group", group))
      );
    } catch (SchedulerException e) {
      LOGGER.error("Cannot fire the job 'SYNC_GROUPS_JOB': Something went wrong", e);
      throw new RuntimeException(e);
    }
  }
}
