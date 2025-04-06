package com.shuttleverse.aggregator.model;

import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Vendor;
import java.util.List;

/**
 * Represents a badminton product.
 */
public interface Product {

  /**
   * Returns the product identifier provided by the vendor.
   *
   * @return The product identifier
   */
  String getProductId();

  /**
   * Returns the name or title of the product.
   *
   * @return The product name
   */
  String getName();

  /**
   * Returns the brand of the product.
   *
   * @return The product brand
   */
  Brand getBrand();

  /**
   * Returns the vendor selling this product.
   *
   * @return The vendor
   */
  Vendor getVendor();

  /**
   * Returns the URL to the product page on the vendor's website.
   *
   * @return The vendor URL
   */
  String getVendorUrl();

  /**
   * Returns the list of available variants for this product.
   *
   * <p>Variants typically represent different options available for the same product (e.g.,
   * different colors, sizes, or configurations).
   *
   * @return The list of variants
   */
  List<Variant> getVariants();

  /**
   * Returns the list of image URLs for this product.
   *
   * @return The list of image source URLs
   */
  List<String> getImageSources();

  /**
   * Sets the product identifier.
   *
   * @param productId The product identifier to set
   */
  void setProductId(String productId);

  /**
   * Sets the product name.
   *
   * @param name The name to set
   */
  void setName(String name);

  /**
   * Sets the product brand.
   *
   * @param brand The brand to set
   */
  void setBrand(Brand brand);

  /**
   * Sets the vendor selling this product.
   *
   * @param vendor The vendor to set
   */
  void setVendor(Vendor vendor);

  /**
   * Sets the URL to the product page on the vendor's website.
   *
   * @param vendorUrl The vendor URL to set
   */
  void setVendorUrl(String vendorUrl);

  /**
   * Sets the list of available variants for this product.
   *
   * @param variants The list of variants to set
   */
  void setVariants(List<Variant> variants);

  /**
   * Sets the list of image URLs for this product.
   *
   * @param imageSources The list of image source URLs to set
   */
  void setImageSources(List<String> imageSources);
}

