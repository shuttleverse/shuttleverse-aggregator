package com.shuttleverse.aggregator.service;

import com.shuttleverse.aggregator.api.model.ApiProduct;
import com.shuttleverse.aggregator.configs.VendorConfig;
import com.shuttleverse.aggregator.api.model.ProductApiResponse;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendorService {
  private final VendorConfig vendorConfig;
  private final WebClient webClient;

  public <T extends ApiProduct> ProductApiResponse<T> fetchProductData(String vendorName, String brand, String category) {
    String apiUrl = getVendorUrl(vendorName, brand, category);
    return webClient.get().uri(apiUrl).retrieve().bodyToMono(new ParameterizedTypeReference<ProductApiResponse<T>>() {}).block();
  }

  private String getVendorUrl(String vendorName, String brand, String category) {
    VendorConfig.Vendor vendor = vendorConfig.getVendors().get(vendorName);
    StringBuilder url = new StringBuilder();
    if (vendor == null) {
      throw new IllegalArgumentException("Vendor not found");
    }

    String baseUrl = vendor.getBaseUrl();
    String productBrand = vendor.getBrands().get(brand);
    String productCategory = vendor.getCategories().get(category);
    if (productCategory == null) {
      throw new IllegalArgumentException("Category not found");
    }

    return url.append(baseUrl)
        .append(productBrand)
        .append(productCategory)
        .append("/products.json")
        .toString();
  }

}
