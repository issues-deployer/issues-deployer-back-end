package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.net.URL;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@AllArgsConstructor
@Builder
public final class GitLabProjectDto implements Serializable {

  private static final long serialVersionUID = 4358872179594352733L;

  @SerializedName("id")
  private final long remoteId;

  @Nullable
  private final String description;

  @NotNull
  private final String name;

  @SerializedName("path")
  @NotNull
  private final String path;

  @SerializedName("web_url")
  @NotNull
  private final URL webUrl;

  @SerializedName("last_activity_at")
  @NotNull
  private final String lastActivityAt;

  @SerializedName("namespace")
  @NotNull
  private final GitLabNamespaceDto namespace;

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }
}
