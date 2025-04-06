package com.shuttleverse.aggregator.builder;

import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Product;
import com.shuttleverse.aggregator.model.Variant;
import java.util.List;

/**
 * Builder interface for creating Product instances.
 *
 * <p>This interface defines a generic builder pattern for constructing different types of
 * badminton products.
 *
 * @param <M> The type of product being built, must extend {@link Product}
 * @param <B> The type of the builder itself, must extend {@link ProductBuilder} (this recursive
 *            type parameter enables method chaining with the correct return type)
 */
public interface ProductBuilder<M extends Product, B extends ProductBuilder<M, B>> {

  /**
   * Sets the product identifier.
   *
   * @param productId The product identifier to set
   * @return This builder instance for method chaining
   */
  B productId(String productId);

  /**
   * Sets the product name.
   *
   * @param name The name to set
   * @return This builder instance for method chaining
   */
  B name(String name);

  /**
   * Sets the product brand.
   *
   * @param brand The brand to set
   * @return This builder instance for method chaining
   */
  B brand(Brand brand);

  /**
   * Sets the vendor selling this product.
   *
   * @param vendor The vendor to set
   * @return This builder instance for method chaining
   */
  B vendor(Vendor vendor);

  /**
   * Sets the URL to the product page on the vendor's website.
   *
   * @param vendorUrl The vendor URL to set
   * @return This builder instance for method chaining
   */
  B vendorUrl(String vendorUrl);

  /**
   * Sets the list of available variants for this product.
   *
   * @param variants The list of variants to set
   * @return This builder instance for method chaining
   */
  B variants(List<Variant> variants);

  /**
   * Sets the list of image URLs for this product.
   *
   * @param imageSources The list of image source URLs to set
   * @return This builder instance for method chaining
   */
  B imageSources(List<String> imageSources);

  /**
   * Builds and returns a new product instance with the properties set on this builder.
   *
   * <p>This method should validate that all required properties have been set before constructing
   * the final product instance.
   *
   * @return A new product instance
   */
  M build();
}
