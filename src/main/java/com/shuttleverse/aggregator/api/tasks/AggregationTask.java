package com.shuttleverse.aggregator.api.tasks;

import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct;
import com.shuttleverse.aggregator.api.model.ProductApiResponse;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.service.ProductService;
import com.shuttleverse.aggregator.service.RacketService;
import com.shuttleverse.aggregator.service.ShuttleService;
import com.shuttleverse.aggregator.service.VendorService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled tasks for aggregating badminton product information from multiple vendors.
 *
 * <p>This component periodically fetches product data (e.g., rackets, shuttles) from various
 * vendors and updates the internal system with the latest information.
 *
 * <p>Products are aggregated asynchronously for better performance and scalability.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class AggregationTask {

  private final VendorService vendorService;
  private final RacketService racketService;
  private final ShuttleService shuttleService;
  private final List<Vendor> vendors = List.of(Vendor.values());
  private static final Logger logger = LoggerFactory.getLogger(AggregationTask.class);
  private final String cronExpression = "0 17 * * * *";

  /**
   * Aggregates racket data asynchronously for all vendors based on a set schedule.
   */
  @Scheduled(cron = cronExpression)
  @Async
  public void aggregateRacketsAsync() {
    aggregateProductAsync(Category.RACKET, racketService);
  }

  /**
   * Aggregates shuttle data asynchronously for all vendors based on a set schedule.
   */
  @Scheduled(cron = cronExpression)
  @Async
  public void aggregateShuttlesAsync() {
    aggregateProductAsync(Category.SHUTTLE, shuttleService);
  }

  /**
   * Aggregates shoe data asynchronously for all vendors based on a set schedule.
   */
  @Scheduled(cron = cronExpression)
  @Async
  public void aggregateShoesAsync() {
    System.out.println("Aggregate shoes async");
    // TODO
  }

  /**
   * Aggregates apparel data asynchronously for all vendors based on a set schedule.
   */
  @Scheduled(cron = cronExpression)
  @Async
  public void aggregateApparelsSync() {
    // TODO
  }

  /**
   * Asynchronously aggregates product data for a given category using a specified service.
   *
   * @param category       the product category (e.g., RACKET, SHUTTLE)
   * @param productService the service responsible for processing and saving the product data
   * @param <T>            a type that extends {@link ApiBadmintonProduct}
   */
  private <T extends ApiBadmintonProduct> void aggregateProductAsync(Category category,
      ProductService<T>
          productService) {
    List<CompletableFuture<Void>> futures = vendors.stream()
        .map(vendor -> CompletableFuture.runAsync(
            () -> fetchAndUpdate(vendor, category, productService),
            Executors.newCachedThreadPool()))
        .toList();

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
  }

  /**
   * Fetches and updates product data from a specific vendor for a given category.
   *
   * <p>Handles exceptions and logs errors if fetching or updating fails.
   * </p>
   *
   * @param vendor         the vendor to fetch data from
   * @param category       the product category
   * @param productService the service responsible for processing and saving the product data
   * @param <T>            a type that extends {@link ApiBadmintonProduct}
   */
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
