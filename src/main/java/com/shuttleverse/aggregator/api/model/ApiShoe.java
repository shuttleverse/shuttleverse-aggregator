package com.shuttleverse.aggregator.api.model;

import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Shoe;
import com.shuttleverse.aggregator.model.Variant;

import java.util.stream.Collectors;

public class ApiShoe extends ApiProduct {

  public Shoe convertToShoe(Vendor vendor) {
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
