package com.shuttleverse.aggregator.model;

import java.util.Date;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * Represents the price history of a badminton product.
 */
@AllArgsConstructor
@Builder
@Setter
@Getter
public class BadmintonProductPriceHistory implements ProductPriceHistory {

  @Id
  private String id;
  private String productId;
  private String productName;
  private Map<String, Double> variantPrices;
  private Date timestamp;

  @Override
  public Map<String, Double> getProductPrices() {
    return this.variantPrices;
  }
}
