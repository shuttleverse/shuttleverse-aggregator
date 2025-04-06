package com.shuttleverse.aggregator.api.model;

import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Racket;
import com.shuttleverse.aggregator.utils.ApiModelConverter;

/**
 * Represents a badminton racket fetched from a vendor API.
 *
 * <p>Extends {@link ApiBadmintonProduct} and provides functionality to convert the API response
 * into the internal {@link Racket} model used within the system.
 * </p>
 */
public class ApiRacket extends ApiBadmintonProduct {

  @Override
  public Racket convertToModel(Vendor vendor, Category category) {
    ApiModelConverter<Racket> converter = new ApiModelConverter<>(Racket.builder());

    return converter.apply(this, vendor, category);
  }
}
