package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.net.URL;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
@Getter
public final class GitLabGroupDto implements Serializable {

  private static final long serialVersionUID = 8134241300991642789L;

  private final long id;

  @NotNull
  @SerializedName("web_url")
  private final URL webUrl;

  @NotNull
  private final String name;

  @NotNull
  private final String path;

  @NotNull
  @SerializedName("full_name")
  private final String fullName;

  @Nullable
  private final String description;

  @Nullable
  @SerializedName("avatar_url")
  private final URL avatarUrl;

  @Nullable
  @SerializedName("parent_id")
  private final Long parentId;

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public Optional<URL> getAvatarUrl() {
    return Optional.ofNullable(avatarUrl);
  }

  public Optional<Long> getParentId() {
    return Optional.ofNullable(parentId);
  }
}
