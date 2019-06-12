package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer;

import com.vaddemgen.issuesdeployer.gitlabclient.businesslayer.GitLabAccount;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.CacheableEntity;
import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.GitLabGroupDto;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
class GitLabCacheDataMapperImpl implements GitLabCacheDataMapper {

  @Override
  @Cacheable(value = "gitLabGroups", key = "#gitLabAccount.token")
  public CacheableEntity<ArrayList<GitLabGroupDto>> cacheGroups(
      @NotNull GitLabAccount gitLabAccount,
      @NotNull List<GitLabGroupDto> groups,
      @NotNull Duration ttl
  ) {
    return new CacheableEntity<>(new ArrayList<>(groups), LocalDateTime.now().plus(ttl));
  }

  @Override
  @Cacheable(value = "gitLabGroups", key = "#gitLabAccount.token",
      unless = "#result.getEntity().isEmpty()")
  public CacheableEntity<ArrayList<GitLabGroupDto>> getCachedGroups(
      @NotNull GitLabAccount gitLabAccount) {
    return new CacheableEntity<>(new ArrayList<>(), null);
  }

  @Override
  @CacheEvict(value = "gitLabGroups", key = "#gitLabAccount.token")
  public void evictGroupsCache(@NotNull GitLabAccount gitLabAccount) {
  }
}
