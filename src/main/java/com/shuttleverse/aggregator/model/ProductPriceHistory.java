package com.shuttleverse.aggregator.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Document(collection = "product_price_history")
public interface ProductPriceHistory {
  String getProductId();

  Map<String, Double> getProductPrices();

  Date getTimestamp();
}
