package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
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
public final class SuperGroup extends Group {

  @NotNull
  private List<SubGroup> subGroups = Collections.emptyList();

  @Builder
  public SuperGroup(
      @NotNull String code,
      @NotNull String shortName,
      @NotNull List<Project> projects,
      @Nullable URL webUrl,
      @Nullable String name,
      @Nullable String description,
      @Nullable String path,
      @NotNull List<SubGroup> subGroups
  ) {
    super(code, shortName, projects, webUrl, name, description, path);
    this.subGroups = subGroups;
  }

  public Stream<SubGroup> getSubGroups() {
    return subGroups.stream();
  }
}
