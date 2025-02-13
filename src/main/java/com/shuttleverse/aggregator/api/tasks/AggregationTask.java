package com.shuttleverse.aggregator.api.tasks;

import com.shuttleverse.aggregator.api.model.ApiRacket;
import com.shuttleverse.aggregator.api.model.ProductApiResponse;
import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.service.RacketService;
import com.shuttleverse.aggregator.service.VendorService;

import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AggregationTask {
  private final VendorService vendorService;
  private final RacketService racketService;

  @Scheduled()
  @Async
  public void aggregateRacketsAsync() {
    try {
      ProductApiResponse<ApiRacket> apiRacketResponse = vendorService.fetchProductData(Vendor.YUMO, Brand.YONEX, Category.RACKET);
      racketService.addRackets(apiRacketResponse.products(), Vendor.YUMO);
    } catch (Exception e) {
      LoggerFactory.getLogger(AggregationTask.class).error(e.getMessage(), e);
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
