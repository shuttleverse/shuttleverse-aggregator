package com.shuttleverse.aggregator.utils;

import com.shuttleverse.aggregator.enums.Currency;

public class PriceConverter {
  public static double convert(Currency currency, double price) {
    return price * currency.getFactor();
  }
}
