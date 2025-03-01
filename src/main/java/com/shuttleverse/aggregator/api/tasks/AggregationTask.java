package com.shuttleverse.aggregator.api.tasks;

import com.shuttleverse.aggregator.api.model.ApiRacket;
import com.shuttleverse.aggregator.api.model.ProductApiResponse;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.service.RacketService;
import com.shuttleverse.aggregator.service.VendorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
  private final List<Vendor> VENDORS = List.of(Vendor.values());
  private static final Logger logger = LoggerFactory.getLogger(AggregationTask.class);

  @Scheduled()
//  @EventListener(ApplicationReadyEvent.class)
  @Async
  public void aggregateRacketsAsync() {
    List<CompletableFuture<Void>> futures = VENDORS.stream()
        .map(vendor -> CompletableFuture.runAsync(() -> fetchAndUpdate(vendor),
            Executors.newCachedThreadPool()))
        .toList();

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
  }

  private void fetchAndUpdate(Vendor vendor) {
    try {
      ProductApiResponse<ApiRacket> apiRacketResponse =
          vendorService.fetchProductData(vendor, Category.RACKET);
      racketService.updateRacketsList(apiRacketResponse.products(), vendor);
    } catch (Exception e) {
      logger.error("Failed to fetch/update rackets for vendor {}: {}", vendor, e.getMessage(), e);
    }
  }


  @Scheduled()
  @Async
  public void aggregateShoesAsync() {

  }

  @Scheduled()
  @Async
  public void aggregateApparelsSync() {

  }
}
