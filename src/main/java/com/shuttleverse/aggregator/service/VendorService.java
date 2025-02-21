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

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VendorService {
  private final VendorConfig vendorConfig;
  private final WebClient webClient;
  private final ObjectMapper objectMapper;

  public <T extends ApiProduct> ProductApiResponse<T> fetchProductData(Vendor vendor,
                                                                       Brand brand,
                                                                       Category category) {
    String apiUrl = getVendorUrl(vendor, brand, category);
    JavaType javaType = getProductType(category);

    return webClient.get()
        .uri(apiUrl)
        .retrieve()
        .bodyToMono(String.class)
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

  public <T extends ApiProduct> ProductApiResponse<T> fetchProductData(Vendor vendor,
                                                                       Category category) {
    StringBuilder sb = new StringBuilder(getVendorUrl(vendor, category));
    String pageOneUrl = sb.append("?limit=250&page=1").toString();
    String pageTwoUrl = sb.append("?limit=250&page=2").toString();

    JavaType javaType = getProductType(category);

    Mono<ProductApiResponse<T>> responseMono1 = webClient.get()
        .uri(pageOneUrl)
        .retrieve()
        .bodyToMono(String.class)
        .flatMap(responseBody -> deserializeResponse(responseBody, javaType));

    Mono<ProductApiResponse<T>> responseMono2 = webClient.get()
        .uri(pageTwoUrl)
        .retrieve()
        .bodyToMono(String.class)
        .flatMap(responseBody -> deserializeResponse(responseBody, javaType));

    return Mono.zip(responseMono1, responseMono2, this::mergeResponses)
        .block(); // Block to return synchronously
  }

  private JavaType getProductType(Category category) {
    Class<?> responseType = CategoryResponseMapper.getResponseType(category);
    if (responseType == null) {
      throw new IllegalArgumentException("No response type mapped for category: " + category);
    }

    return objectMapper.getTypeFactory().constructParametricType(ProductApiResponse.class,
        responseType);
  }

  // deserialize JSON response
  private <T extends ApiProduct> Mono<ProductApiResponse<T>> deserializeResponse(String responseBody, JavaType javaType) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      ProductApiResponse<T> response = objectMapper.readValue(responseBody, javaType);
      return Mono.just(response);
    } catch (Exception e) {
      return Mono.error(new RuntimeException("Failed to deserialize response", e));
    }
  }

  // Helper method to merge two API responses
  private <T extends ApiProduct> ProductApiResponse<T> mergeResponses(ProductApiResponse<T> response1,
                                                                      ProductApiResponse<T> response2) {
    List<T> products = response1.products();
    products.addAll(response2.products());

    return new ProductApiResponse<>(products);
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

  private String getVendorUrl(Vendor vendor, Category category) {
    VendorConfig.Vendor vendorFromConfig = vendorConfig.getVendors().get(vendor.toString());
    StringBuilder url = new StringBuilder();
    if (vendorFromConfig == null) {
      throw new IllegalArgumentException("Vendor not found");
    }

    String baseUrl = vendorFromConfig.getBaseUrl();
    String productCategory = vendorFromConfig.getAll().get(category.toString());
    if (productCategory == null) {
      throw new IllegalArgumentException("Category not found");
    }

    return url.append(baseUrl)
        .append(productCategory)
        .append("/products.json")
        .toString();
  }


}
