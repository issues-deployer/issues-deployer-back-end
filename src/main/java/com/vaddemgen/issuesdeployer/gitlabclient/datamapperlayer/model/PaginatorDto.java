package com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model;

import com.vaddemgen.issuesdeployer.gitlabclient.datamapperlayer.model.PaginatorDto.NextPageDetails;
import java.util.Iterator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter(AccessLevel.PACKAGE)
public final class PaginatorDto implements Iterable<NextPageDetails> {

  private final int totalPages;
  private final int perPage;

  @NotNull
  @Override
  public Iterator<NextPageDetails> iterator() {
    return new PaginatorIterator(this);
  }

  @AllArgsConstructor(access = AccessLevel.PACKAGE)
  @Getter
  public static final class NextPageDetails {

    private final int nextPage;
    private final int perPage;
  }
}

class PaginatorIterator implements Iterator<NextPageDetails> {

  private final int totalPages;
  private final int perPage;
  private int currentPage;

  PaginatorIterator(PaginatorDto paginatorDto) {
    currentPage = 0;
    totalPages = paginatorDto.getTotalPages();
    perPage = paginatorDto.getPerPage();
  }

  @Override
  public void remove() {
    currentPage = currentPage > 0 ? currentPage - 1 : 0;
  }

  @Override
  public boolean hasNext() {
    return currentPage < totalPages;
  }

  @Override
  public NextPageDetails next() {
    return new NextPageDetails(++currentPage, perPage);
  }
}