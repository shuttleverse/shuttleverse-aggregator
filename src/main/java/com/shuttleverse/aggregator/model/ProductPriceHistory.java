package com.shuttleverse.aggregator.model;

import java.util.Date;
import java.util.Map;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Interface representing the price history of a product.
 */
@Document(collection = "product_price_history")
public interface ProductPriceHistory {

  /**
   * Gets the ID of the product.
   *
   * @return the product ID.
   */
  String getProductId();

  /**
   * Gets the prices of the product variants. The map key represents the variant, and the value
   * represents the price.
   *
   * @return a map of variant prices.
   */
  Map<String, Double> getProductPrices();

  /**
   * Gets the timestamp when the price data was recorded.
   *
   * @return the timestamp of the price history entry.
   */
  Date getTimestamp();
}
