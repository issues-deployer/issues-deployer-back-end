package com.vaddemgen.issuesdeployer.client.gitlab.businesslayer.model;

import java.io.Serializable;
import java.net.URL;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public final class GitLabIssueDto implements Serializable {

  private static final long serialVersionUID = 2852036698601623927L;

  private final int number;
  private final int projectId;
  private final String title;
  private final String description;
  private final String[] labels;
  private final URL webUrl;

  public GitLabIssueDto(int number, int projectId, String title, String description,
      String[] labels, URL webUrl) {
    this.number = number;
    this.projectId = projectId;
    this.title = title;
    this.description = description;
    this.labels = labels;
    this.webUrl = webUrl;
  }

  public int getNumber() {
    return number;
  }

  public int getProjectId() {
    return projectId;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String[] getLabels() {
    return labels;
  }

  public URL getWebUrl() {
    return webUrl;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
