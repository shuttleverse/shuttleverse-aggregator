package com.shuttleverse.aggregator.utils;

import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Product;

/**
 * Functional interface for converting API product representations to domain model products.
 *
 * <p>This converter transforms API-specific product data into the application's domain model,
 * applying business rules and normalizing data during conversion. Implementations should handle any
 * necessary data transformations, such as price conversions or field mappings.
 *
 * @param <M> The type of domain model product to convert to, must extend {@link Product}
 */
@FunctionalInterface
public interface ApiProductConverter<M extends Product> {

  /**
   * Converts an API product representation to a domain model product.
   *
   * <p>This method applies any necessary transformations to convert from the external API
   * representation to the internal domain model, including vendor-specific logic.
   *
   * @param apiProduct The API product data to convert
   * @param vendor     The vendor associated with this product
   * @param category   The category this product belongs to
   * @return A properly initialized domain model product
   */
  M apply(ApiBadmintonProduct apiProduct, Vendor vendor, Category category);
}
