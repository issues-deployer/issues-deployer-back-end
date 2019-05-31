package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import java.net.URL;
import java.util.List;
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
public final class SubGroup extends Group {

  @Builder
  public SubGroup(@NotNull String code,
      @NotNull String shortName,
      @NotNull List<Project> projects,
      @Nullable URL webUrl,
      @Nullable String name,
      @Nullable String description,
      @Nullable String path) {
    super(code, shortName, projects, webUrl, name, description, path);
  }
}
