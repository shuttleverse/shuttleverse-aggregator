package com.shuttleverse.aggregator.service;

import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct;
import com.shuttleverse.aggregator.model.BadmintonProductPriceHistory;
import com.shuttleverse.aggregator.model.BadmintonProduct;
import com.shuttleverse.aggregator.model.ProductPriceHistory;
import com.shuttleverse.aggregator.model.Variant;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BadmintonProductService<T extends ApiBadmintonProduct> implements ProductService<T> {
  private final ProductHistoryService productHistoryService;

  protected void addProductPriceHistory(BadmintonProduct badmintonProduct) {
    ProductPriceHistory history = BadmintonProductPriceHistory.builder()
        .productId(badmintonProduct.getProductId())
        .productName(badmintonProduct.getName())
        .variantPrices(this.convertVariantListToMap(badmintonProduct.getVariants()))
        .timestamp(new Date())
        .build();
    productHistoryService.addProductHistory(history);
  }

  private Map<String, Double> convertVariantListToMap(List<Variant> rackets) {
    Map<String, Double> variantMap = new HashMap<>();

    for (Variant variant : rackets) {
      // Sanitize the title by removing invalid characters
      String sanitizedTitle = sanitizeKey(variant.getTitle());
      variantMap.put(sanitizedTitle, variant.getPrice());
    }

    return variantMap;
  }

  private String sanitizeKey(String title) {
    // Remove any character that's not alphanumeric (letters and numbers)
    return title.replaceAll("[^a-zA-Z0-9]", ""); // Removes everything that's not a letter or number
  }
}
