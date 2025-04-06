package com.shuttleverse.aggregator.utils;

import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct;
import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct.Image;
import com.shuttleverse.aggregator.builder.ProductBuilder;
import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Currency;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Product;
import com.shuttleverse.aggregator.model.Variant;
import java.util.stream.Collectors;

/**
 * Converts API badminton product models to internal domain product models.
 *
 * <p>This converter transforms API-specific badminton product data into the application's domain
 * model, applying business rules such as vendor-specific price conversions and field mappings. It
 * uses the builder pattern to create product domain objects.
 *
 * @param <M> The type of domain model product to convert to, must extend {@link Product}
 */
public class ApiModelConverter<M extends Product> implements
    ApiProductConverter<M> {

  private final ProductBuilder<M, ?> builder;

  /**
   * Constructs a new API model converter with the specified product builder.
   *
   * @param builder A product builder for the target domain model type
   */
  public ApiModelConverter(ProductBuilder<M, ?> builder) {
    this.builder = builder;
  }

  @Override
  public M apply(ApiBadmintonProduct apiProduct, Vendor vendor, Category category) {
    return builder
        .productId(apiProduct.getProductId())
        .name(apiProduct.getTitle())
        .brand(Brand.fromString(apiProduct.getBrand()))
        .vendor(vendor)
        .vendorUrl(apiProduct.getVendorUrl(vendor, category))
        .variants(apiProduct.getVariants().stream()
            .map(variant -> Variant.builder()
                .title(variant.getTitle())
                .price(vendor == Vendor.YUMO
                    ? PriceConverter.convert(Currency.CAD, variant.getPrice())
                    : variant.getPrice())
                .build())
            .collect(Collectors.toList()))
        .imageSources(apiProduct.getImages().stream()
            .map(Image::src)
            .collect(Collectors.toList()))
        .build();
  }
}
