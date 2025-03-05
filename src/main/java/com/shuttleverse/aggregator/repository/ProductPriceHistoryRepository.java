package com.shuttleverse.aggregator.repository;

import com.shuttleverse.aggregator.model.ProductPriceHistory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceHistoryRepository extends MongoRepository<ProductPriceHistory, String> {
  boolean existsByProductId(String productId);
}
