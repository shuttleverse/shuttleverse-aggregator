package com.shuttleverse.aggregator.enums;

import lombok.NonNull;

public enum Brand {
  YONEX, LINING, VICTOR;

  public static Brand fromString(@NonNull String brand) {
    return Brand.valueOf(brand.toUpperCase());
  }

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
