package com.shuttleverse.aggregator.api.model;

import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Currency;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Racket;
import com.shuttleverse.aggregator.model.Variant;
import com.shuttleverse.aggregator.utils.PriceConverter;

import java.util.stream.Collectors;

public class ApiRacket extends ApiBadmintonProduct {

  public Racket convertToModel(Vendor vendor, Category category) {
    return Racket.builder()
        .productId(this.productId)
        .name(this.title)
        .brand(Brand.fromString(this.brand))
        .vendor(vendor)
        .vendorUrl(this.getVendorUrl(vendor, category))
        .variants(this.variants.stream().map(variant -> Variant.builder()
                .title(variant.getTitle())
                .price(vendor == Vendor.YUMO ?
                    PriceConverter.convert(Currency.CAD, variant.getPrice()) :
                    variant.getPrice())
                .build())
            .collect(Collectors.toList()))
        .imageSources(this.images.stream().map(Image::src).collect(Collectors.toList()))
        .build();
  }
}
