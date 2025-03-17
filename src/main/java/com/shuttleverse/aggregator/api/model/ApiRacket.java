package com.shuttleverse.aggregator.api.model;

import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Currency;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Racket;
import com.shuttleverse.aggregator.model.Variant;
import com.shuttleverse.aggregator.utils.PriceConverter;
import java.util.stream.Collectors;

/**
 * Represents a badminton racket fetched from a vendor API.
 *
 * <p>Extends {@link ApiBadmintonProduct} and provides functionality to convert the API response
 * into the internal {@link Racket} model used within the system.
 * </p>
 */
public class ApiRacket extends ApiBadmintonProduct {

  /**
   * Converts this API racket object into a {@link Racket} domain model.
   *
   * <p>This method maps API fields to the internal Racket model, adjusts pricing based on the
   * vendor
   * (e.g., converts prices from CAD to USD if necessary), and compiles variant and image
   * information.
   * </p>
   *
   * @param vendor   the vendor from which the racket was fetched
   * @param category the product category (used to generate the vendor-specific URL)
   * @return a {@link Racket} object populated with the relevant data
   */
  public Racket convertToModel(Vendor vendor, Category category) {
    return Racket.builder()
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
