package com.shuttleverse.aggregator.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shuttleverse.aggregator.configs.VendorConfig;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Abstract representation of a badminton product fetched from various vendor APIs. This class
 * provides common fields shared by all badminton products and utility methods for generating
 * vendor-specific product URLs.
 *
 * <p>Fields are mapped to JSON properties to facilitate deserialization of API responses.</p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public abstract class ApiBadmintonProduct implements ApiProduct {

  @NonNull
  @JsonProperty("id")
  protected String productId;
  @NonNull
  protected String title;
  @NonNull
  protected String handle;
  @NonNull
  @JsonProperty("vendor")
  protected String brand;
  @NonNull
  protected List<ApiVariant> variants;
  @NonNull
  protected List<Image> images;

  /**
   * Represents a product image with a source URL.
   *
   * @param src the URL of the image
   */
  @JsonIgnoreProperties(ignoreUnknown = true)
  public record Image(String src) {

  }

  /**
   * Generates a vendor-specific product URL based on the vendor and product category. Handles
   * different URL structures depending on the vendor's formatting.
   *
   * @param vendor   the vendor enum representing the product's vendor
   * @param category the category of the product (e.g., rackets, shoes)
   * @return the full URL linking to the product on the vendor's website
   * @throws IllegalArgumentException if vendor is not supported
   */
  public String getVendorUrl(Vendor vendor, Category category) {
    VendorConfig.Vendor vendorFromConfig = VendorConfig.getInstance().getVendors()
        .get(vendor.toString());
    StringBuilder url = new StringBuilder(vendorFromConfig.getBaseUrl());
    switch (vendor) {
      case YUMO, BADMINTON_AVENUE, BADMINTON_WAREHOUSE ->
          url.append(vendorFromConfig.getAll().get(category.toString())).append("/products").append(
              "/").append(this.handle);
      case JOY, NYDHI -> {
        int index = url.lastIndexOf("/");
        if (index != -1) {
          url.setLength(index);
        }
        url.append("/products/").append(this.handle);
      }
      default -> throw new IllegalArgumentException("Unexpected value: " + vendor);
    }
    return url.toString();
  }

}