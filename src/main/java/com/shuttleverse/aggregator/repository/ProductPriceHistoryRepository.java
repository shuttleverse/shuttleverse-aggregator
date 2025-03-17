package com.shuttleverse.aggregator.repository;

import com.shuttleverse.aggregator.model.ProductPriceHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for interacting with the product price history data in MongoDB.
 */
@Repository
public interface ProductPriceHistoryRepository extends
    MongoRepository<ProductPriceHistory, String> {

  /**
   * Checks whether a product price history exists for a given product ID.
   *
   * @param productId the product ID to check
   * @return {@code true} if a product price history exists for the given product ID; {@code false}
   *     otherwise.
   */
  boolean existsByProductId(String productId);
}
