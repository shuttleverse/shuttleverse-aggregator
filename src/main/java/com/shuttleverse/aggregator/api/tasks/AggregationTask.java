package com.shuttleverse.aggregator.api.tasks;

import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct;
import com.shuttleverse.aggregator.api.model.ProductApiResponse;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.BadmintonProduct;
import com.shuttleverse.aggregator.model.Product;
import com.shuttleverse.aggregator.service.ProductService;
import com.shuttleverse.aggregator.service.RacketService;
import com.shuttleverse.aggregator.service.ShuttleService;
import com.shuttleverse.aggregator.service.VendorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AggregationTask {
  private final VendorService vendorService;
  private final RacketService racketService;
  private final ShuttleService shuttleService;
  private final List<Vendor> VENDORS = List.of(Vendor.values());
  private static final Logger logger = LoggerFactory.getLogger(AggregationTask.class);

  @Scheduled()
//  @EventListener(ApplicationReadyEvent.class)
  @Async
  public void aggregateRacketsAsync() {
    aggregateProductAsync(Category.RACKET, racketService);
  }

  @Scheduled()
//  @EventListener(ApplicationReadyEvent.class)
  @Async
  public void aggregateShuttlesAsync() {
    aggregateProductAsync(Category.SHUTTLE, shuttleService);
  }


  @Scheduled()
  @Async
  public void aggregateShoesAsync() {

  }

  @Scheduled()
  @Async
  public void aggregateApparelsSync() {

  }

  private <T extends ApiBadmintonProduct> void aggregateProductAsync(Category category, ProductService<T> productService) {
    List<CompletableFuture<Void>> futures = VENDORS.stream()
        .map(vendor -> CompletableFuture.runAsync(() -> fetchAndUpdate(vendor, category, productService),
            Executors.newCachedThreadPool()))
        .toList();

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
  }

  private <T extends ApiBadmintonProduct> void fetchAndUpdate(Vendor vendor,
                                                              Category category,
                                                              ProductService<T> productService) {
    try {
      ProductApiResponse<T> apiRacketResponse =
          vendorService.fetchProductData(vendor, category);
      productService.updateProductInformation(apiRacketResponse.products(), vendor);
    } catch (Exception e) {
      logger.error("Failed to fetch/update rackets for vendor {}: {}", vendor, e.getMessage(), e);
    }
  }
}
