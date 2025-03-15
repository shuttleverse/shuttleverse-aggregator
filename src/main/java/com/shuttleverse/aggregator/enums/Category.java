package com.shuttleverse.aggregator.enums;

public enum Category {
  RACKET, SHOE, APPAREL, SHUTTLE;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
