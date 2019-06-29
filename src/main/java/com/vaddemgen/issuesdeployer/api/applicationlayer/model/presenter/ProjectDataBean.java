package com.vaddemgen.issuesdeployer.api.applicationlayer.model.presenter;

import static org.apache.commons.lang3.time.DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Project;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@Builder
public final class ProjectDataBean {

  @NonNull
  private long id;

  @NonNull
  private String code;

  @NonNull
  private String name;

  @NonNull
  private String webUrl;

  private String description;
  private String lastActivityAt;

  public static ProjectDataBean of(Project project) {
    return ProjectDataBean.builder()
        .id(project.getId())
        .code(project.getCode())
        .name(project.getName())
        .webUrl(project.getWebUrl().toExternalForm())
        .description(project.getDescription().orElse(null))
        .lastActivityAt(
            project.getLastActivityAt()
                .map(ProjectDataBean::dateToString)
                .orElse(null)
        )
        .build();
  }

  private static String dateToString(ZonedDateTime lastActivityAt) {
    return ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(
        Date.from(lastActivityAt.toInstant()));
  }

  public Optional<String> getDescription() {
    return Optional.ofNullable(description);
  }

  public Optional<String> getLastActivityAt() {
    return Optional.ofNullable(lastActivityAt);
  }
}
