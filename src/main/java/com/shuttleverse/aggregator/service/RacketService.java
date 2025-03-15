package com.shuttleverse.aggregator.service;

import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct;
import com.shuttleverse.aggregator.api.model.ApiRacket;
import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Racket;
import com.shuttleverse.aggregator.repository.RacketRepository;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RacketService extends BadmintonProductService<ApiRacket> implements ProductService<ApiRacket> {
  private final RacketRepository racketRepository;

  public RacketService(ProductHistoryService productHistoryService, RacketRepository racketRepository) {
    super(productHistoryService);
    this.racketRepository = racketRepository;
  }

  @Override
  public void updateProductInformation(List<ApiRacket> rackets, Vendor vendor) {
    Set<String> productIds = new HashSet<>();
    List<Racket> newRackets = new ArrayList<>();
    for (ApiRacket apiRacket : rackets) {
      if (!isSupportedBrand(apiRacket) || productIds.contains(apiRacket.getProductId())) {
        continue;
      }
      productIds.add(apiRacket.getProductId());
      Racket racket = apiRacket.convertToModel(vendor, Category.RACKET);
      addProductPriceHistory(racket);

      Optional<Racket> existingRacketOpt = racketRepository.findByProductId(racket.getProductId());
      if (existingRacketOpt.isPresent()) {
        Racket existingRacket = existingRacketOpt.get();
        existingRacket.setVendorUrl(racket.getVendorUrl());
        existingRacket.setVariants(racket.getVariants());
      } else {
        newRackets.add(racket);
      }
    }
    racketRepository.saveAll(newRackets);

    deleteOldProducts(vendor, productIds);
  }

  private void deleteOldProducts(Vendor vendor, Set<String> newProductIds) {
    List<String> productIdsToDelete = new ArrayList<>(newProductIds);
    racketRepository.deleteByVendorAndProductIdNotIn(vendor, productIdsToDelete);
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
