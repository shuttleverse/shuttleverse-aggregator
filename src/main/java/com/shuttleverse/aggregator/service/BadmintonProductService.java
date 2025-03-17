package com.shuttleverse.aggregator.service;

import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct;
import com.shuttleverse.aggregator.model.BadmintonProduct;
import com.shuttleverse.aggregator.model.BadmintonProductPriceHistory;
import com.shuttleverse.aggregator.model.ProductPriceHistory;
import com.shuttleverse.aggregator.model.Variant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/**
 * Abstract service class for managing Badminton product-related operations.
 *
 * @param <T> the type of {@link ApiBadmintonProduct}
 * @see ProductService for the common interface this class extends.
 */
@RequiredArgsConstructor
public abstract class BadmintonProductService<T extends ApiBadmintonProduct> implements
    ProductService<T> {

  private final ProductHistoryService productHistoryService;

  /**
   * Adds the price history for a given Badminton product.
   *
   * @param badmintonProduct the product for which the price history is being added.
   */
  protected void addProductPriceHistory(BadmintonProduct badmintonProduct) {
    ProductPriceHistory history = BadmintonProductPriceHistory.builder()
        .productId(badmintonProduct.getProductId())
        .productName(badmintonProduct.getName())
        .variantPrices(this.convertVariantListToMap(badmintonProduct.getVariants()))
        .timestamp(new Date())
        .build();
    productHistoryService.addProductHistory(history);
  }

  /**
   * Converts a list of variants into a map of variant titles to prices.
   *
   * @param rackets the list of variants to be converted.
   * @return a map where the key is the variant title and the value is the variant price.
   */
  private Map<String, Double> convertVariantListToMap(List<Variant> rackets) {
    Map<String, Double> variantMap = new HashMap<>();

    for (Variant variant : rackets) {
      // Sanitize the title by removing invalid characters
      String sanitizedTitle = sanitizeKey(variant.getTitle());
      variantMap.put(sanitizedTitle, variant.getPrice());
    }

    return variantMap;
  }

  /**
   * Sanitizes a variant title by removing non-alphanumeric characters.
   *
   * @param title the variant title to be sanitized.
   * @return the sanitized title with only alphanumeric characters.
   */
  private String sanitizeKey(String title) {
    return title.replaceAll("[^a-zA-Z0-9]", "");
  }
}
