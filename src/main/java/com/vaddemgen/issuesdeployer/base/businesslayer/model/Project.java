package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import java.net.URL;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class Project {

  @NotNull
  private String code;

  @NotNull
  private String name;

  @Nullable
  private String description;

  @Nullable
  private String path;

  @Nullable
  private ZonedDateTime createdAt;

  @Nullable
  private ZonedDateTime lastActivityAt;

  @Nullable
  private URL webUrl;
}
