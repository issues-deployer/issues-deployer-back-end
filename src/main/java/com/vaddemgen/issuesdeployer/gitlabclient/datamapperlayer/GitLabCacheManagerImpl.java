package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer;

import static java.util.Collections.unmodifiableList;

import com.vaddemgen.issuesdeployer.gitlabclient.businesslayer.GitLabAccount;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.GitLabGroupDto;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public final class GitLabCacheManagerImpl implements GitLabCacheManager {

  private final GitLabCacheDataMapper dataMapper;

  public GitLabCacheManagerImpl(@NotNull GitLabCacheDataMapper dataMapper) {
    this.dataMapper = dataMapper;
  }

  @Override
  public void cacheGroups(
      @NotNull GitLabAccount gitLabAccount,
      @NotNull List<GitLabGroupDto> groups,
      @NotNull Duration ttl
  ) {
    if (!groups.isEmpty()) {
      dataMapper.cacheGroups(gitLabAccount, groups, ttl);
    }
  }

  @Override
  public synchronized Optional<List<GitLabGroupDto>> getCachedGroups(
      @NotNull GitLabAccount gitLabAccount) {
    var cachedEntity = dataMapper.getCachedGroups(gitLabAccount);
    if (cachedEntity.nonExpired() && cachedEntity.getEntity().size() > 0) {
      return Optional.of(unmodifiableList(cachedEntity.getEntity()));
    }
    dataMapper.evictGroupsCache(gitLabAccount);
    return Optional.empty();
  }
}
