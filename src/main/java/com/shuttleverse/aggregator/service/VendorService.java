package com.shuttleverse.aggregator.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuttleverse.aggregator.api.model.ApiProduct;
import com.shuttleverse.aggregator.configs.VendorConfig;
import com.shuttleverse.aggregator.api.model.ProductApiResponse;
import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.mapper.CategoryResponseMapper;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VendorService {
  private final VendorConfig vendorConfig;
  private final WebClient webClient;

  @SuppressWarnings("unchecked") // Suppress unchecked cast warning
  public <T extends ApiProduct> ProductApiResponse<T> fetchProductData(Vendor vendor,
                                                                       Brand brand,
                                                                       Category category) {
    String apiUrl = getVendorUrl(vendor, brand, category);

    // Get the response type based on the category
    Class<T> responseType = (Class<T>) CategoryResponseMapper.getResponseType(category);
    if (responseType == null) {
      throw new IllegalArgumentException("No response type mapped for category: " + category);
    }

    ObjectMapper objectMapper = new ObjectMapper();
    JavaType javaType = objectMapper.getTypeFactory()
        .constructParametricType(ProductApiResponse.class, responseType);

    return webClient.get()
        .uri(apiUrl)
        .retrieve()
        .bodyToMono(String.class) // First, get the response as a String
        .flatMap(responseBody -> {
          try {
            // Deserialize the response body into the correct type
            ProductApiResponse<T> response = objectMapper.readValue(responseBody, javaType);
            return Mono.just(response);
          } catch (Exception e) {
            return Mono.error(new RuntimeException("Failed to deserialize response", e));
          }
        })
        .block(); // Block to make the call synchronous
  }

  private String getVendorUrl(Vendor vendor, Brand brand, Category category) {
    VendorConfig.Vendor vendorFromConfig = vendorConfig.getVendors().get(vendor.toString());
    StringBuilder url = new StringBuilder();
    if (vendorFromConfig == null) {
      throw new IllegalArgumentException("Vendor not found");
    }

    String baseUrl = vendorFromConfig.getBaseUrl();
    String productBrand = vendorFromConfig.getBrands().get(brand.toString());
    String productCategory = vendorFromConfig.getCategories().get(category.toString());
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
