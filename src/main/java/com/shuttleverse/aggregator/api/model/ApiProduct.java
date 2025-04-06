package com.shuttleverse.aggregator.api.model;

import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct.Image;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Product;
import java.util.List;

/**
 * Represents a product retrieved from an external API.
 *
 * <p>This interface defines the contract for product models that come from vendor APIs. It
 * specifies methods to access and modify product information as it appears in those external
 * systems, providing a standardized interface for working with products across different vendor
 * APIs.
 *
 * <p>Implementations of this interface should handle the specific details of how each vendor
 * represents their product data.
 */
public interface ApiProduct {

  /**
   * Returns the product identifier as provided by the API.
   *
   * @return The product identifier
   */
  String getProductId();

  /**
   * Returns the title or name of the product as provided by the API.
   *
   * @return The product title
   */
  String getTitle();

  /**
   * Returns the handle (URL-friendly identifier) of the product.
   *
   * <p>The handle is typically used in constructing URLs for the product and may contain the
   * product name in a URL-safe format.
   *
   * @return The product handle
   */
  String getHandle();

  /**
   * Returns the brand name of the product as provided by the API.
   *
   * @return The brand name
   */
  String getBrand();

  /**
   * Returns the list of variants for this product as provided by the API.
   *
   * <p>Variants typically represent different options available for the same product (e.g.,
   * different colors, sizes, or configurations).
   *
   * @return The list of API variants
   */
  List<ApiVariant> getVariants();

  /**
   * Returns the list of images for this product as provided by the API.
   *
   * @return The list of product images
   */
  List<Image> getImages();

  /**
   * Constructs and returns the URL to the product page on the vendor's website.
   *
   * <p>This method generates the appropriate URL based on the vendor and category, typically using
   * the product handle or ID in combination with the vendor's URL structure.
   *
   * @param vendor   The vendor for this product
   * @param category The category this product belongs to
   * @return The complete URL to the product page
   */
  String getVendorUrl(Vendor vendor, Category category);

  /**
   * Sets the product identifier.
   *
   * @param productId The product identifier to set
   */
  void setProductId(String productId);

  /**
   * Sets the title or name of the product.
   *
   * @param title The title to set
   */
  void setTitle(String title);

  /**
   * Sets the handle (URL-friendly identifier) of the product.
   *
   * @param handle The handle to set
   */
  void setHandle(String handle);

  /**
   * Sets the brand name of the product.
   *
   * @param brand The brand name to set
   */
  void setBrand(String brand);

  /**
   * Sets the list of variants for this product.
   *
   * @param variants The list of API variants to set
   */
  void setVariants(List<ApiVariant> variants);

  /**
   * Sets the list of images for this product.
   *
   * @param images The list of product images to set
   */
  void setImages(List<Image> images);

  /**
   * Converts this API shuttle object into a {@link Product} domain model.
   *
   * @param vendor   the vendor from which the shuttle product was fetched
   * @param category the product category (used for vendor URL generation)
   * @return a {@link Product} object populated with the relevant data
   */
  Product convertToModel(Vendor vendor, Category category);
}
