package com.vaddemgen.issuesdeployer.api.applicationlayer.model.presenter;

import lombok.Builder;
import lombok.NonNull;

public final class SubGroupDataBean extends GroupDataBean {

  @Builder
  public SubGroupDataBean(
      long id,
      @NonNull String code,
      @NonNull String shortName,
      @NonNull String name,
      @NonNull String webUrl,
      String description
  ) {
    super(id, code, shortName, name, webUrl, description);
  }
}
