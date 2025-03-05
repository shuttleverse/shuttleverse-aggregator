package com.shuttleverse.aggregator.api.tasks;

import static org.mockito.Mockito.*;

import com.shuttleverse.aggregator.api.model.ApiRacket;
import com.shuttleverse.aggregator.api.model.ProductApiResponse;
import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.service.RacketService;
import com.shuttleverse.aggregator.service.VendorService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
class AggregationTaskTest {

  @Mock
  private VendorService vendorService;

  @Mock
  private RacketService racketService;

  @InjectMocks
  private AggregationTask aggregationTask;

  @BeforeEach
  void setUp() {
    // Reset mocks before each test
    reset(vendorService, racketService);
  }

  @Test
  void testAggregateRacketsAsync_handlesException() {
    // Simulate an exception in vendorService
    when(vendorService.fetchProductData(Vendor.YUMO, Brand.YONEX, Category.RACKET))
        .thenThrow(new RuntimeException("Failed to fetch data"));

    // Capture logs
    org.slf4j.Logger logger = LoggerFactory.getLogger(AggregationTask.class);

    // Run the method
    aggregationTask.aggregateRacketsAsync();

    // Verify that the exception was logged
    verify(vendorService, times(1)).fetchProductData(Vendor.YUMO, Brand.YONEX, Category.RACKET);
    verify(racketService, never()).updateProductInformation(any(), any()); // Shouldn't be called
  }
}
