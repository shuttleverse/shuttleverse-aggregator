package com.shuttleverse.aggregator.service;

import com.shuttleverse.aggregator.api.model.ApiProduct;
import com.shuttleverse.aggregator.api.model.ApiRacket;
import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Racket;
import com.shuttleverse.aggregator.repository.RacketRepository;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RacketService {
  private final RacketRepository racketRepository;

  public void updateRacketsList(List<ApiRacket> rackets, Vendor vendor) {
    List<String> productIds = new ArrayList<>();
    List<Racket> newRackets = rackets.stream()
        .filter(this::isSupportedBrand)
        .map(apiRacket -> {
          productIds.add(apiRacket.getProductId());

          Racket racket = apiRacket.convertToRacket(vendor);
          Optional<Racket> existingRacket =
              racketRepository.findByProductId(racket.getProductId());

          if (existingRacket.isPresent()) {
            Racket existing = existingRacket.get();
            existing.setVendorUrl(racket.getVendorUrl());
            existing.setVariants(racket.getVariants());
            return existing;
          } else {
            return racket;
          }
        })
        .toList();

    racketRepository.saveAll(newRackets);
    deleteOldProducts(vendor, productIds);
  }


  private void deleteOldProducts(Vendor vendor, List<String> newProductIds) {
    racketRepository.deleteByVendorAndProductIdNotIn(vendor, newProductIds);
  }

  private boolean isSupportedBrand(ApiProduct apiProduct) {
    String brand = StringUtils.trimAllWhitespace(apiProduct.getBrand().toUpperCase());
    try {
      Brand.valueOf(brand);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
