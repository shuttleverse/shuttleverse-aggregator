package com.shuttleverse.aggregator.utils;

import com.shuttleverse.aggregator.enums.Currency;

public class PriceConverter {

  public static double convert(Currency currency, double price) {
    return Math.round((price * currency.getFactor()) * 100.0) / 100.0;
  }
}
