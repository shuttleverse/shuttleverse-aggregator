package com.shuttleverse.aggregator.enums;

/**
 * Enum representing the categories of badminton products.
 */
public enum Category {
  RACKET, SHOE, APPAREL, SHUTTLE;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
