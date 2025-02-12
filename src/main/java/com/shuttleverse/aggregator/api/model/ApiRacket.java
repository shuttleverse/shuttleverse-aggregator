package com.shuttleverse.aggregator.api.model;

import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Currency;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Racket;
import com.shuttleverse.aggregator.model.Variant;
import com.shuttleverse.aggregator.utils.PriceConverter;

import java.util.stream.Collectors;

public class ApiRacket extends ApiProduct {
  public Racket convertToRacket(Vendor vendor) {
    return Racket.builder()
        .name(this.title)
        .brand(Brand.fromString(vendor.name()))
        .vendor(vendor)
        .vendorUrl("")
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
