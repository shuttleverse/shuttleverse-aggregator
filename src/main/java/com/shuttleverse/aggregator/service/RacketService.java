package com.shuttleverse.aggregator.service;

import com.shuttleverse.aggregator.api.model.ApiRacket;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Racket;
import com.shuttleverse.aggregator.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RacketService {
  private final ProductRepository<Racket> productRepository;

  public void addRackets(List<ApiRacket> rackets, Vendor vendor) {
    List<Racket> newRackets = rackets.stream()
        .map(apiRacket -> {
          Racket racket = apiRacket.convertToRacket(vendor);
          Optional<Racket> existingRacket =
              productRepository.findByNameAndVendor(racket.getName(), vendor);

          if (existingRacket.isPresent()) {
            Racket existing = existingRacket.get();
            existing.setVariants(racket.getVariants());
            return existing;
          } else {
            return racket;
          }
        })
        .toList();

    // Save all rackets (both new and updated)
    productRepository.saveAll(newRackets);
  }
}
