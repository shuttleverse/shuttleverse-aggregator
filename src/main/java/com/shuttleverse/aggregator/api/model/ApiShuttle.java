package com.shuttleverse.aggregator.api.model;

import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Currency;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Shuttle;
import com.shuttleverse.aggregator.model.Variant;
import com.shuttleverse.aggregator.utils.PriceConverter;
import java.util.stream.Collectors;

/**
 * Represents a badminton shuttle product fetched from a vendor API.
 *
 * <p>Extends {@link ApiBadmintonProduct} and provides a method to convert the API response into
 * the internal {@link Shuttle} domain model used within the system.
 * </p>
 */
public class ApiShuttle extends ApiBadmintonProduct {

  /**
   * Converts this API shuttle object into a {@link Shuttle} domain model.
   *
   * @param vendor   the vendor from which the shuttle product was fetched
   * @param category the product category (used for vendor URL generation)
   * @return a {@link Shuttle} object populated with the relevant data
   */
  public Shuttle convertToModel(Vendor vendor, Category category) {
    return Shuttle.builder()
        .productId(this.productId)
        .name(this.title)
        .brand(Brand.fromString(this.brand))
        .vendor(vendor)
        .vendorUrl(this.getVendorUrl(vendor, category))
        .variants(this.variants.stream().map(variant -> Variant.builder()
                .title(variant.getTitle())
                .price(vendor == Vendor.YUMO
                    ? PriceConverter.convert(Currency.CAD, variant.getPrice()) :
                    variant.getPrice())
                .build())
            .collect(Collectors.toList()))
        .imageSources(this.images.stream().map(Image::src).collect(Collectors.toList()))
        .build();
  }
}
