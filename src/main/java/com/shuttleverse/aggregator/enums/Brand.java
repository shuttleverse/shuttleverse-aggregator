package com.shuttleverse.aggregator.enums;

import org.springframework.util.StringUtils;

import lombok.NonNull;

public enum Brand {
  YONEX, LINING, VICTOR;

  public static Brand fromString(@NonNull String brand) {
    return Brand.valueOf(StringUtils.trimAllWhitespace(brand).toUpperCase());
  }

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
