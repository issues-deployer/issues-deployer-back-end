package com.vaddemgen.issuesdeployer.base.businesslayer.model;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Group {

  @NotNull
  private String code;

  @NotNull
  private String shortName;

  @NotNull
  private List<Project> projects = Collections.emptyList();

  @Nullable
  private URL webUrl;

  @Nullable
  private String name;

  @Nullable
  private String description;

  @Nullable
  private String path;

  public Stream<Project> getProjects() {
    return projects.stream();
  }
}
