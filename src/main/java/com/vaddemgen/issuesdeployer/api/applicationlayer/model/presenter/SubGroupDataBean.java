package com.vaddemgen.issuesdeployer.api.applicationlayer.model.presenter;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SubGroup;
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

  public static SubGroupDataBean of(SubGroup subGroup) {
    return SubGroupDataBean.builder()
        .id(subGroup.getId())
        .code(subGroup.getCode())
        .shortName(subGroup.getShortName())
        .name(subGroup.getName())
        .webUrl(subGroup.getWebUrl().toExternalForm())
        .description(subGroup.getDescription().orElse(null))
        .build();
  }
}
