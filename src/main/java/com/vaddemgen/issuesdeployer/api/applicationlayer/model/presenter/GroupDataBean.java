package com.vaddemgen.issuesdeployer.api.applicationlayer.model.presenter;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
abstract class GroupDataBean {

  private final long id;

  @NonNull
  private final String code;

  @NonNull
  private final String shortName;

  @NonNull
  private final String name;

  @NonNull
  private final String webUrl;

  private final String description;

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }
}
