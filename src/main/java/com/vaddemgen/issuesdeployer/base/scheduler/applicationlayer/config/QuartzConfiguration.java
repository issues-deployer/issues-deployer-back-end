package com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.config;

import com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job.SyncAllIssuesJob;
import com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job.SyncGitAccountsJob;
import com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job.SyncIssuesJob;
import com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job.SyncProjectsJob;
import com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job.SyncSubGroupsJob;
import com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job.SyncSuperGroupsJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzConfiguration {

  private static final String TRIGGER_GROUP_GROUPS = "GROUPS";
  private static final String TRIGGER_GROUP_GIT_ACCOUNTS = "GIT_ACCOUNTS";
  private static final String TRIGGER_GROUP_PROJECTS = "PROJECTS";
  private static final String TRIGGER_GROUP_ISSUES = "ISSUES";

  @Bean
  public CronTriggerFactoryBean syncGitAccountsScheduledTrigger(
      @Qualifier("syncGitAccountsBean") JobDetail jobDetail) {
    var trigger = new CronTriggerFactoryBean();
    trigger.setJobDetail(jobDetail);
    trigger.setCronExpression("0 0 0 * * ?");
    trigger.setGroup(TRIGGER_GROUP_GIT_ACCOUNTS);
    return trigger;
  }

  @Bean
  public CronTriggerFactoryBean syncGitAccountsTrigger(
      @Qualifier("syncAllIssuesJobBean") JobDetail jobDetail) {
    var trigger = new CronTriggerFactoryBean();
    trigger.setJobDetail(jobDetail);
    trigger.setCronExpression("0 0 * * * ?");
    trigger.setGroup(TRIGGER_GROUP_ISSUES);
    return trigger;
  }

  @Bean
  public JobDetailFactoryBean syncGitAccountsBean() {
    var jobDetailFactoryBean = new JobDetailFactoryBean();
    jobDetailFactoryBean.setJobClass(SyncGitAccountsJob.class);
    jobDetailFactoryBean.setName(SyncGitAccountsJob.JOB_SYNC_GIT_ACCOUNTS);
    jobDetailFactoryBean.setGroup(TRIGGER_GROUP_GROUPS);
    jobDetailFactoryBean.setDurability(true);
    return jobDetailFactoryBean;
  }

  @Bean
  public JobDetailFactoryBean syncAllIssuesJobBean() {
    var jobDetailFactoryBean = new JobDetailFactoryBean();
    jobDetailFactoryBean.setJobClass(SyncAllIssuesJob.class);
    jobDetailFactoryBean.setName(SyncAllIssuesJob.JOB_ALL_SYNC_ISSUES);
    jobDetailFactoryBean.setGroup(TRIGGER_GROUP_ISSUES);
    jobDetailFactoryBean.setDurability(true);
    return jobDetailFactoryBean;
  }

  @Bean
  public JobDetailFactoryBean syncSuperGroupsJobBean() {
    var jobDetailFactoryBean = new JobDetailFactoryBean();
    jobDetailFactoryBean.setJobClass(SyncSuperGroupsJob.class);
    jobDetailFactoryBean.setName(SyncSuperGroupsJob.JOB_SYNC_SUPER_GROUPS);
    jobDetailFactoryBean.setGroup(TRIGGER_GROUP_GROUPS);
    jobDetailFactoryBean.setDurability(true);
    return jobDetailFactoryBean;
  }

  @Bean
  public JobDetailFactoryBean syncSubGroupsJobBean() {
    var jobDetailFactoryBean = new JobDetailFactoryBean();
    jobDetailFactoryBean.setJobClass(SyncSubGroupsJob.class);
    jobDetailFactoryBean.setName(SyncSubGroupsJob.JOB_SYNC_SUB_GROUPS);
    jobDetailFactoryBean.setGroup(TRIGGER_GROUP_GROUPS);
    jobDetailFactoryBean.setDurability(true);
    return jobDetailFactoryBean;
  }

  @Bean
  public JobDetailFactoryBean syncProjectsJobBean() {
    var jobDetailFactoryBean = new JobDetailFactoryBean();
    jobDetailFactoryBean.setJobClass(SyncProjectsJob.class);
    jobDetailFactoryBean.setName(SyncProjectsJob.JOB_SYNC_PROJECTS);
    jobDetailFactoryBean.setGroup(TRIGGER_GROUP_PROJECTS);
    jobDetailFactoryBean.setDurability(true);
    return jobDetailFactoryBean;
  }

  @Bean
  public JobDetailFactoryBean syncIssuesJobBean() {
    var jobDetailFactoryBean = new JobDetailFactoryBean();
    jobDetailFactoryBean.setJobClass(SyncIssuesJob.class);
    jobDetailFactoryBean.setName(SyncIssuesJob.JOB_SYNC_ISSUES);
    jobDetailFactoryBean.setGroup(TRIGGER_GROUP_ISSUES);
    jobDetailFactoryBean.setDurability(true);
    return jobDetailFactoryBean;
  }
}
