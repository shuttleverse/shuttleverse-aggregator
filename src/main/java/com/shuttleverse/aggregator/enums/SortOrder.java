package com.shuttleverse.aggregator.enums;

import lombok.NonNull;

/**
 * Enum representing the sort order for data.
 *
 * <p>This enum supports two sorting orders: ASCENDING and DESCENDING. It provides a utility method
 * to convert a string representation of the sort order into the corresponding enum value.
 * </p>
 */
public enum SortOrder {
  ASCENDING, DESCENDING;

  /**
   * Converts a string to a corresponding {@link SortOrder} enum, ignoring case.
   *
   * @param sortOrder the string representation of the sort order (e.g., "ascending", "descending")
   * @return the corresponding {@link SortOrder} enum value
   * @throws IllegalArgumentException if the sortOrder string doesn't match any enum value
   */
  public static SortOrder fromString(@NonNull String sortOrder) {
    return SortOrder.valueOf(sortOrder.toUpperCase());
  }
}
