package com.vaddemgen.issuesdeployer.api.applicationlayer.model.presenter;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import lombok.Builder;
import lombok.NonNull;

public final class SuperGroupDataBean extends GroupDataBean {

  @Builder
  public SuperGroupDataBean(
      long id,
      @NonNull String code,
      @NonNull String shortName,
      @NonNull String name,
      @NonNull String webUrl,
      String description
  ) {
    super(id, code, shortName, name, webUrl, description);
  }

  public static SuperGroupDataBean of(SuperGroup superGroup) {
    return builder()
        .id(superGroup.getId())
        .code(superGroup.getCode())
        .shortName(superGroup.getShortName())
        .name(superGroup.getName())
        .webUrl(superGroup.getWebUrl().toExternalForm())
        .description(superGroup.getDescription().orElse(null))
        .build();
  }
}
