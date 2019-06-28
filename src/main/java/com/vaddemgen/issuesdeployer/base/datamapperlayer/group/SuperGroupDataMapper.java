package com.vaddemgen.issuesdeployer.base.datamapperlayer.group;

import com.vaddemgen.issuesdeployer.base.businesslayer.model.gitaccount.GitAccount;
import com.vaddemgen.issuesdeployer.base.businesslayer.model.group.SuperGroup;
import java.util.List;
import java.util.stream.Stream;

public interface SuperGroupDataMapper {

  Stream<SuperGroup> findSuperGroups(GitAccount gitAccount);

  /**
   * <p>Merges the super groups with stored entities. Deletes the entities that are not in the
   * super groups list.</p>
   *
   * @param gitAccount  The owner of the super groups.
   * @param superGroups The super groups to be merged into current entities.
   * @return Updated super groups
   */
  Stream<SuperGroup> mergeSuperGroups(GitAccount gitAccount, List<SuperGroup> superGroups);

  Stream<SuperGroup> createSuperGroups(GitAccount gitAccount, List<SuperGroup> superGroups);
}
