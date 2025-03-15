package com.shuttleverse.aggregator.service;

import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct;
import com.shuttleverse.aggregator.api.model.ApiShuttle;
import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Shuttle;
import com.shuttleverse.aggregator.repository.ShuttleRepository;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ShuttleService extends BadmintonProductService<ApiShuttle> implements ProductService<ApiShuttle> {
  private final ShuttleRepository shuttleRepository;

  public ShuttleService(ProductHistoryService productHistoryService,
                        ShuttleRepository shuttleRepository) {
    super(productHistoryService);
    this.shuttleRepository = shuttleRepository;
  }

  @Override
  public void updateProductInformation(List<ApiShuttle> shuttles, Vendor vendor) {
    Set<String> productIds = new HashSet<>();
    List<Shuttle> newShuttles = new ArrayList<>();

    for (ApiShuttle apiShuttle : shuttles) {
      if (!isSupportedBrand(apiShuttle) || productIds.contains(apiShuttle.getProductId())) {
        continue;
      }
      productIds.add(apiShuttle.getProductId());
      Shuttle shuttle = apiShuttle.convertToModel(vendor, Category.SHUTTLE);
      addProductPriceHistory(shuttle);

      Optional<Shuttle> existingShuttleOpt =
          shuttleRepository.findByProductId(shuttle.getProductId());
      if (existingShuttleOpt.isPresent()) {
        Shuttle existingShuttle = existingShuttleOpt.get();
        existingShuttle.setVendorUrl(shuttle.getVendorUrl());
        existingShuttle.setVariants(shuttle.getVariants());
      } else {
        newShuttles.add(shuttle);
      }
    }
    shuttleRepository.saveAll(newShuttles);

    deleteOldProducts(vendor, productIds);
  }

  private void deleteOldProducts(Vendor vendor, Set<String> newProductIds) {
    List<String> productIdsToDelete = new ArrayList<>(newProductIds);
    shuttleRepository.deleteByVendorAndProductIdNotIn(vendor, productIdsToDelete);
  }

  private boolean isSupportedBrand(ApiBadmintonProduct apiBadmintonProduct) {
    String brand = StringUtils.trimAllWhitespace(apiBadmintonProduct.getBrand().toUpperCase());
    try {
      Brand.valueOf(brand);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
