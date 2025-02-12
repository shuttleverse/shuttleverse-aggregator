package com.shuttleverse.aggregator.enums;

import lombok.Getter;

@Getter
public enum Currency {
  CAD(0.70);

  private final double factor;

  Currency(double factor) {
    this.factor = factor;
  }

}
