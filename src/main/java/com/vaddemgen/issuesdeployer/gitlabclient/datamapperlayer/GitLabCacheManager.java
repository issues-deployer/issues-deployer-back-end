package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer;

import com.vaddemgen.issuesdeployer.gitlabclient.businesslayer.GitLabAccount;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.GitLabGroupDto;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Cache manager for the GitLab data mappers.
 */
public interface GitLabCacheManager {

  /**
   * Caches groups on the period {@code ttl}.
   *
   * @param gitLabAccount The cache key
   * @param groups        The cache value
   * @param ttl           Cache time to live
   */
  void cacheGroups(
      @NotNull GitLabAccount gitLabAccount,
      @NotNull List<GitLabGroupDto> groups,
      @NotNull Duration ttl
  );

  /**
   * Returns cached groups or {@code Optional.empty()} if the cache is expired or does'n exist.
   *
   * @param gitLabAccount The cache key
   */
  Optional<List<GitLabGroupDto>> getCachedGroups(@NotNull GitLabAccount gitLabAccount);
}
