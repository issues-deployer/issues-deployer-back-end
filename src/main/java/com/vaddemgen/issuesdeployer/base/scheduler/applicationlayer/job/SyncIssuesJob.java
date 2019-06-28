package com.vaddemgen.issuesdeployer.base.scheduler.applicationlayer.job;

import static java.util.Objects.requireNonNull;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.service.IssueService;
import com.vaddemgen.issuesdeployer.base.gitclient.datamapperlayer.GitClientFactory;
import java.io.IOException;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class SyncIssuesJob extends QuartzJobBean {

  public static final String JOB_SYNC_ISSUES = "JOB_SYNC_ISSUES";

  private static final Logger LOGGER = LoggerFactory.getLogger(SyncIssuesJob.class);

  private final GitClientFactory gitClientFactory;
  private final IssueService issueService;

  public SyncIssuesJob(
      @NonNull GitClientFactory gitClientFactory,
      @NonNull IssueService issueService
  ) {
    this.gitClientFactory = gitClientFactory;
    this.issueService = issueService;
  }

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    LOGGER.trace("{}: Started", JOB_SYNC_ISSUES);

    var gitAccount = (GitAccount) requireNonNull(context.getMergedJobDataMap().get("gitAccount"));
    var project = (Project) requireNonNull(context.getMergedJobDataMap().get("project"));

    LOGGER.trace(
        "{}: Git account: {}, Group: {}",
        JOB_SYNC_ISSUES,
        gitAccount,
        project
    );

    var gitClient = gitClientFactory.createGitClient(gitAccount);

    try {
      issueService.mergeIssues(
          project,
          gitClient.findIssues(project).collect(Collectors.toList())
      );
    } catch (RuntimeException | IOException | InterruptedException e) {
      String message = String.format(
          "%s: Something went wrong for git account: %s, project: %s",
          JOB_SYNC_ISSUES,
          gitAccount,
          project
      );
      LOGGER.error(message, e);
      throw new JobExecutionException(e);
    }

    LOGGER.trace("{}: Finished", JOB_SYNC_ISSUES);
  }
}
