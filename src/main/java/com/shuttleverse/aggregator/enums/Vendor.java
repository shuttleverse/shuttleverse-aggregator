package com.shuttleverse.aggregator.enums;

import lombok.NonNull;

/**
 * Enum representing different vendors that sells badminton products.
 */
public enum Vendor {
  YUMO, JOY, BADMINTON_AVENUE, NYDHI, BADMINTON_WAREHOUSE;

  /**
   * Converts a string to the corresponding {@link Vendor} enum, ignoring case.
   *
   * @param vendor the string representation of the vendor (e.g., "Yumo", "joy")
   * @return the corresponding {@link Vendor} enum value
   * @throws IllegalArgumentException if the vendor string doesn't match any enum value
   */
  public static Vendor fromString(@NonNull String vendor) {
    return Vendor.valueOf(vendor.toUpperCase());
  }

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
