package com.shuttleverse.aggregator.api.model;

import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Shuttle;
import com.shuttleverse.aggregator.utils.ApiModelConverter;

/**
 * Represents a badminton shuttle product fetched from a vendor API.
 *
 * <p>Extends {@link ApiBadmintonProduct} and provides a method to convert the API response into
 * the internal {@link Shuttle} domain model used within the system.
 * </p>
 */
public class ApiShuttle extends ApiBadmintonProduct {

  @Override
  public Shuttle convertToModel(Vendor vendor, Category category) {
    ApiModelConverter<Shuttle> converter = new ApiModelConverter<>(Shuttle.builder());

    return converter.apply(this, vendor, category);
  }
}
