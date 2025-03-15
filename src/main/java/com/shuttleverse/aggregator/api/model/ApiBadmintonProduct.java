package com.shuttleverse.aggregator.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shuttleverse.aggregator.configs.VendorConfig;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Racket;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public abstract class ApiBadmintonProduct {
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

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record Image(String src) {
  }

  protected String getVendorUrl(Vendor vendor, Category category) {
    VendorConfig.Vendor vendorFromConfig = VendorConfig.getInstance().getVendors().get(vendor.toString());
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
    }
    return url.toString();
  }

}