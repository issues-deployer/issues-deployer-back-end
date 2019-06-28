package com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import java.util.Map;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

abstract class AbstractSyncProjectsJob extends QuartzJobBean {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSyncProjectsJob.class);

  private final JobDetail syncProjectsJobDetail;

  AbstractSyncProjectsJob(JobDetail syncProjectsJobDetail) {
    this.syncProjectsJobDetail = syncProjectsJobDetail;
  }

  void fireSyncIssuesJob(GitAccount gitAccount, Project project, Scheduler scheduler) {
    try {
      scheduler.triggerJob(
          syncProjectsJobDetail.getKey(),
          new JobDataMap(Map.of("gitAccount", gitAccount, "project", project))
      );
    } catch (SchedulerException e) {
      LOGGER.error("Cannot fire the job 'JOB_SYNC_PROJECTS': Something went wrong", e);
      throw new RuntimeException(e);
    }
  }
}
