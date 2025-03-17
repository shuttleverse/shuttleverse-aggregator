package com.shuttleverse.aggregator.api.model;

import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Shoe;
import com.shuttleverse.aggregator.model.Variant;
import java.util.stream.Collectors;

/**
 * Represents a badminton shoe fetched from a vendor API.
 *
 * <p>Extends {@link ApiBadmintonProduct} and provides functionality to convert the API response
 * into the internal {@link Shoe} domain model used within the system.
 * </p>
 */
public class ApiShoe extends ApiBadmintonProduct {

  /**
   * Converts this API shoe object into a {@link Shoe} domain model.
   *
   * <p>This method maps API fields such as product ID, title, brand, variants, and images
   * to the internal {@link Shoe} model. The vendor URL is currently set to an empty string and can
   * be populated as needed.
   * </p>
   *
   * @param vendor   the vendor from which the shoe was fetched
   * @param category the product category (used for consistency or future URL generation)
   * @return a {@link Shoe} object populated with the relevant data
   */
  public Shoe convertToModel(Vendor vendor, Category category) {
    return Shoe.builder()
        .productId(this.productId)
        .name(this.title)
        .brand(Brand.fromString(this.brand))
        .vendor(vendor)
        .vendorUrl("")
        .variants(this.variants.stream().map(variant -> Variant.builder()
                .title(variant.getTitle())
                .price(variant.getPrice())
                .build())
            .collect(Collectors.toList()))
        .imageSources(this.images.stream().map(Image::src).collect(Collectors.toList()))
        .build();
  }
}
