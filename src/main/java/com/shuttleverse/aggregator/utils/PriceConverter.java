package com.shuttleverse.aggregator.utils;

import com.shuttleverse.aggregator.enums.Currency;

/**
 * Utility class for converting prices to different currencies.
 */
public class PriceConverter {

  /**
   * Converts a given price to a specified currency by applying the corresponding conversion
   * factor.
   *
   * @param currency the target currency to convert the price to
   * @param price    the original price to be converted
   * @return the price converted to the target currency, rounded to two decimal places
   */
  public static double convert(Currency currency, double price) {
    return Math.round((price * currency.getFactor()) * 100.0) / 100.0;
  }
}
