package com.shuttleverse.aggregator.service;

import com.shuttleverse.aggregator.model.ProductPriceHistory;
import com.shuttleverse.aggregator.repository.ProductPriceHistoryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for managing product price history.
 */
@Service
@RequiredArgsConstructor
public class ProductHistoryService {

  private final ProductPriceHistoryRepository productPriceHistoryRepository;

  /**
   * Finds the product price history by the product ID.
   *
   * @param productId the ID of the product whose price history is to be found.
   * @return an {@link Optional} containing the product price history if found, or an empty
   *     {@link Optional} if no history exists for the product.
   */
  public Optional<ProductPriceHistory> findByProductId(String productId) {
    return productPriceHistoryRepository.findById(productId);
  }

  /**
   * Adds a new product price history record.
   *
   * @param productPriceHistory the product price history to be saved.
   */
  public void addProductHistory(ProductPriceHistory productPriceHistory) {
    productPriceHistoryRepository.save(productPriceHistory);
  }
}
