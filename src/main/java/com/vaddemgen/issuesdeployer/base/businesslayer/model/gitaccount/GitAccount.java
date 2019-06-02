package com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.DomainModel;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface GitAccount extends DomainModel {

  @Nullable
  User getUser();

  @NotNull
  @Override
  GitAccountBuilder clonePartially();

  interface GitAccountBuilder {

    @NotNull
    GitAccountBuilder user(@Nullable User user);

    @NotNull
    GitAccount build();
  }
}
