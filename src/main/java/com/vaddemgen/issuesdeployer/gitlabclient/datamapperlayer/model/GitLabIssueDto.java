package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.net.URL;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@Builder
public final class GitLabIssueDto implements Serializable {

  private static final long serialVersionUID = -855054258380401196L;

  @SerializedName("id")
  private final int remoteId;

  @NonNull
  @SerializedName("iid")
  private final String code;

  @NonNull
  private final String title;

  @NonNull
  private final String[] labels;

  @NonNull
  @SerializedName("web_url")
  private final URL webUrl;

  @NonNull
  @SerializedName("updated_at")
  private final String updatedAt;

  public Stream<String> getLabels() {
    return Stream.of(labels);
  }
}
