package com.shuttleverse.aggregator.api.model;

import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Shoe;
import com.shuttleverse.aggregator.utils.ApiModelConverter;

/**
 * Represents a badminton shoe fetched from a vendor API.
 *
 * <p>Extends {@link ApiBadmintonProduct} and provides functionality to convert the API response
 * into the internal {@link Shoe} domain model used within the system.
 * </p>
 */
public class ApiShoe extends ApiBadmintonProduct {

  @Override
  public Shoe convertToModel(Vendor vendor, Category category) {
    ApiModelConverter<Shoe> converter = new ApiModelConverter<>(Shoe.builder());

    return converter.apply(this, vendor, category);
  }
}
