package com.shuttleverse.aggregator.enums;

import lombok.Getter;

/**
 * Enum representing different currencies supported in the system.
 *
 * <p>The conversion factor represents the relative value of this currency against USD.
 * </p>
 */
@Getter
public enum Currency {
  CAD(0.70);

  private final double factor;

  Currency(double factor) {
    this.factor = factor;
  }

}
