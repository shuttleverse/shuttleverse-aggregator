package com.shuttleverse.aggregator.service;

import com.shuttleverse.aggregator.model.ProductPriceHistory;
import com.shuttleverse.aggregator.repository.ProductPriceHistoryRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductHistoryService {
  private final ProductPriceHistoryRepository productPriceHistoryRepository;

  public Optional<ProductPriceHistory> findByProductId(String productId) {
    return productPriceHistoryRepository.findById(productId);
  }

  public void addProductHistory(ProductPriceHistory productPriceHistory) {
    productPriceHistoryRepository.save(productPriceHistory);
  }
}
