package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model;

import static java.util.Objects.isNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
@Getter
public final class CacheableEntity<T extends Serializable> implements Serializable {

  private static final long serialVersionUID = -480167998392405259L;

  @NotNull
  private final T entity;

  @Nullable
  private final LocalDateTime deadline;

  public boolean nonExpired() {
    return isNull(deadline) || deadline.isAfter(LocalDateTime.now());
  }
}
