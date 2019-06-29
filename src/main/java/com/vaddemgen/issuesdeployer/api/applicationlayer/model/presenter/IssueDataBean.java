package com.vaddemgen.issuesdeployer.api.applicationlayer.model.presenter;

import static org.apache.commons.lang3.time.DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.Issue;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@Builder
public final class IssueDataBean {

  @NonNull
  private long id;

  @NonNull
  private String code;

  @NonNull
  private String title;

  @NonNull
  private String webUrl;

  @NonNull
  private List<String> labels;

  private String updatedAt;

  public static IssueDataBean of(Issue project) {
    return IssueDataBean.builder()
        .id(project.getId())
        .code(project.getCode())
        .title(project.getTitle())
        .webUrl(project.getWebUrl().toExternalForm())
        .labels(project.getLabels().collect(Collectors.toList()))
        .updatedAt(
            project.getUpdatedAt()
                .map(IssueDataBean::dateToString)
                .orElse(null)
        )
        .build();
  }

  private static String dateToString(ZonedDateTime lastActivityAt) {
    return ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(
        Date.from(lastActivityAt.toInstant()));
  }

  public Optional<String> getUpdatedAt() {
    return Optional.ofNullable(updatedAt);
  }
}
