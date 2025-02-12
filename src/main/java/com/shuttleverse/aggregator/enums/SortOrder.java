package com.shuttleverse.aggregator.enums;

import lombok.NonNull;

public enum SortOrder {
  ASCENDING, DESCENDING;

  public static SortOrder fromString(@NonNull String sortOrder) {
    return SortOrder.valueOf(sortOrder.toUpperCase());
  }
}
