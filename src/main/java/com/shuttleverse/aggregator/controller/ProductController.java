package com.shuttleverse.aggregator.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller interface for exposing product-related APIs.
 *
 * <p>This interface defines a generic endpoint for retrieving products with optional filters like
 * brand, category, price range, and result limit.
 * </p>
 */
public interface ProductController {

  /**
   * Retrieves a list of products based on optional filtering criteria.
   *
   * @param brands     Optional list of brand names to filter products.
   * @param categories Optional list of category names to filter products.
   * @param priceMin   Optional minimum price filter (inclusive).
   * @param priceMax   Optional maximum price filter (inclusive).
   * @param limit      Maximum number of products to return (default is 10).
   * @return {@link ResponseEntity} containing the filtered product list or an appropriate error
   *     response.
   */
  ResponseEntity<?> getProducts(@RequestParam(required = false) List<String> brands,
      @RequestParam(required = false) List<String> categories,
      @RequestParam(required = false) Double priceMin,
      @RequestParam(required = false) Double priceMax,
      @RequestParam(defaultValue = "10") Integer limit);
}
