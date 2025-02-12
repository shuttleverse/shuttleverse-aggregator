package com.shuttleverse.aggregator.enums;

import lombok.NonNull;

public enum Vendor {
  YUMO, JOY, BADMINTON_AVENUE, NYDHI, BADMINTON_WAREHOUSE;

  public static Vendor fromString(@NonNull String vendor) {
    return Vendor.valueOf(vendor.toUpperCase());
  }
}
