package com.shuttleverse.aggregator.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct;
import com.shuttleverse.aggregator.api.model.ProductApiResponse;
import com.shuttleverse.aggregator.configs.VendorConfig;
import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.mapper.CategoryResponseMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Service for fetching product data from external vendors.
 */
@Service
@RequiredArgsConstructor
public class VendorService {

  private final VendorConfig vendorConfig;
  private final WebClient webClient;
  private final ObjectMapper objectMapper;

  /**
   * Fetches product data from a vendor for a specific brand and category.
   *
   * @param vendor   the vendor from which to fetch the data
   * @param brand    the brand of the products
   * @param category the category of the products
   * @param <T>      the type of product being fetched
   * @return a {@link ProductApiResponse} containing the products from the vendor
   */
  public <T extends ApiBadmintonProduct> ProductApiResponse<T> fetchProductData(Vendor vendor,
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

  /**
   * Fetches product data from a vendor for a specific category without filtering by brand.
   *
   * @param vendor   the vendor from which to fetch the data
   * @param category the category of the products
   * @param <T>      the type of product being fetched
   * @return a {@link ProductApiResponse} containing the products from the vendor
   */
  public <T extends ApiBadmintonProduct> ProductApiResponse<T> fetchProductData(Vendor vendor,
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

  /**
   * Retrieves the correct response type for the given category.
   *
   * @param category the category of products to fetch
   * @return the Java type for the product API response
   * @throws IllegalArgumentException if no response type is mapped for the category
   */
  private JavaType getProductType(Category category) {
    Class<?> responseType = CategoryResponseMapper.getResponseType(category);
    if (responseType == null) {
      throw new IllegalArgumentException("No response type mapped for category: " + category);
    }

    return objectMapper.getTypeFactory().constructParametricType(ProductApiResponse.class,
        responseType);
  }

  /**
   * Deserializes the JSON response into the appropriate product API response type.
   *
   * @param responseBody the JSON response body as a string
   * @param javaType     the Java type to deserialize into
   * @param <T>          the type of product
   * @return a {@link Mono} containing the deserialized product API response
   */
  private <T extends ApiBadmintonProduct> Mono<ProductApiResponse<T>> deserializeResponse(
      String responseBody, JavaType javaType) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      ProductApiResponse<T> response = objectMapper.readValue(responseBody, javaType);
      return Mono.just(response);
    } catch (Exception e) {
      return Mono.error(new RuntimeException("Failed to deserialize response", e));
    }
  }

  /**
   * Merges two product API responses by combining their product lists.
   *
   * @param response1 the first product API response
   * @param response2 the second product API response
   * @param <T>       the type of product
   * @return a merged {@link ProductApiResponse} containing all products
   */
  private <T extends ApiBadmintonProduct> ProductApiResponse<T> mergeResponses(
      ProductApiResponse<T> response1,
      ProductApiResponse<T> response2) {
    List<T> products = response1.products();
    products.addAll(response2.products());

    return new ProductApiResponse<>(products);
  }

  /**
   * Constructs the vendor URL for the product API, using the provided vendor, brand, and category.
   *
   * @param vendor   the vendor from which to fetch the products
   * @param brand    the brand of the products
   * @param category the category of the products
   * @return the constructed URL to make the API request
   * @throws IllegalArgumentException if the vendor, brand, or category is not found
   */
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

  /**
   * Constructs the vendor URL for the product API using the provided vendor and category (no
   * brand).
   *
   * @param vendor   the vendor from which to fetch the products
   * @param category the category of the products
   * @return the constructed URL to make the API request
   * @throws IllegalArgumentException if the vendor or category is not found
   */
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
