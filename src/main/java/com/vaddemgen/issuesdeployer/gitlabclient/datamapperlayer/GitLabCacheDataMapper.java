package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer;

import com.vaddemgen.issuesdeployer.gitlabclient.businesslayer.GitLabAccount;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.CacheableEntity;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.GitLabGroupDto;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.GitLabProjectDto;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * <p>Data mapper for the spring cache.</p>
 * <p>Used by {@link GitLabCacheManagerImpl}.</p>
 */
interface GitLabCacheDataMapper {

  /**
   * <p>Caches groups forever, but wraps the cache value with {@code CacheableEntity<T>} to
   * save the time to live parameter.</p>
   *
   * @param gitLabAccount The cache key
   * @param groups        The cache value
   * @param ttl           Time to live
   */
  @SuppressWarnings("UnusedReturnValue")
  CacheableEntity<ArrayList<GitLabGroupDto>> cacheGroups(
      @NotNull GitLabAccount gitLabAccount,
      @NotNull List<GitLabGroupDto> groups,
      @NotNull Duration ttl
  );

  /**
   * <p>Caches projects forever, but wraps the cache value with {@code CacheableEntity<T>} to
   * save the time to live parameter.</p>
   *
   * @param gitLabAccount The cache key
   * @param projects      The cache value
   * @param ttl           Time to live
   */
  @SuppressWarnings("UnusedReturnValue")
  CacheableEntity<ArrayList<GitLabProjectDto>> cacheProjects(
      @NotNull GitLabAccount gitLabAccount,
      @NotNull List<GitLabProjectDto> projects,
      @NotNull Duration ttl
  );

  /**
   * Returns groups cached forever.
   */
  CacheableEntity<ArrayList<GitLabGroupDto>> getCachedGroups(@NotNull GitLabAccount gitLabAccount);

  void evictGroupsCache(@NotNull GitLabAccount gitLabAccount);

  /**
   * Returns projects cached forever.
   */
  CacheableEntity<ArrayList<GitLabProjectDto>> getCachedProjects(
      @NotNull GitLabAccount gitLabAccount);

  void evictProjectsCache(@NotNull GitLabAccount gitLabAccount);
}
