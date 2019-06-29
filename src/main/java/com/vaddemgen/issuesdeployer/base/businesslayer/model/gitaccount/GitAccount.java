package com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.DomainModel;

public interface GitAccount extends DomainModel {

  long getId();

  @Override
  GitAccountBuilder clonePartially();

  interface GitAccountBuilder {

    GitAccountBuilder id(long id);

    GitAccount build();
  }
}
