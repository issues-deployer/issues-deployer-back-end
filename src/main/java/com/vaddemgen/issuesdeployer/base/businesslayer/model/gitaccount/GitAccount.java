package com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.DomainModel;
import org.jetbrains.annotations.NotNull;

public interface GitAccount extends DomainModel {

  long getId();

  @NotNull
  @Override
  GitAccountBuilder clonePartially();

  interface GitAccountBuilder {

    GitAccountBuilder id(long id);

    GitAccount build();
  }
}
