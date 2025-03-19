package com.shuttleverse.aggregator.enums;

import lombok.NonNull;
import org.springframework.util.StringUtils;

/**
 * Enum representing the brands of badminton products.
 */
public enum Brand {
  YONEX, LINING, VICTOR;

  /**
   * Converts a string to a corresponding {@link Brand} enum, ignoring case and trimming any extra
   * whitespace.
   *
   * <p>This method ensures that input strings, even with extra spaces or different cases, are
   * correctly mapped to the appropriate enum value.
   * </p>
   *
   * @param brand the string representation of a brand name
   * @return the corresponding {@link Brand} enum value
   * @throws IllegalArgumentException if the brand string doesn't match any enum value
   */
  public static Brand fromString(@NonNull String brand) {
    return Brand.valueOf(StringUtils.trimAllWhitespace(brand).toUpperCase());
  }

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
