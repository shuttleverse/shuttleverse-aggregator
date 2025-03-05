package com.shuttleverse.aggregator.model;

import java.time.LocalDateTime;
import java.util.Map;

public interface ProductPriceHistory {
  String getProductId();

  Map<String, Double> getProductPrices();

  LocalDateTime getTimestamp();
}
