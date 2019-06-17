package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public final class GitLabNamespaceDto implements Serializable {

  private static final long serialVersionUID = -7298947563968496334L;

  @SerializedName("id")
  private final long remoteId;
}
