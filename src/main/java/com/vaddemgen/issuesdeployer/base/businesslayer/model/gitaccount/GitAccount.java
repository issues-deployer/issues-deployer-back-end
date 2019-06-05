package com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.DomainModel;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface GitAccount extends DomainModel {

  @Nullable
  Long getId();

  @NotNull
  User getUser();

  @NotNull
  @Override
  GitAccountBuilder clonePartially();

  interface GitAccountBuilder {

    GitAccountBuilder id(@Nullable Long id);

    GitAccountBuilder user(@NotNull User user);

    GitAccount build();
  }
}
