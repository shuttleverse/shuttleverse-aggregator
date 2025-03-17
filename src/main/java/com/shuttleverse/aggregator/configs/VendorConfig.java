package com.shuttleverse.aggregator.configs;

import jakarta.annotation.PostConstruct;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration class for managing vendor-specific settings loaded from application properties.
 *
 * <p>This class maps vendor-related configurations, such as base URLs, brand mappings, category
 * mappings, and any additional paths, using Spring Boot's {@link ConfigurationProperties}
 * mechanism.
 * </p>
 *
 * <p>The configuration properties should be defined under the {@code vendors} prefix in the
 * {@code application.yml} or {@code application.properties} file.
 * </p>
 */
@Component
@ConfigurationProperties(prefix = "vendors")
@Getter
@Setter
public class VendorConfig {

  private Map<String, Vendor> vendors;
  @Getter
  private static VendorConfig instance;

  /**
   * Initializes the static instance of this configuration class for later access via
   * {@link #getInstance()}.
   */
  @PostConstruct
  private void registerInstance() {
    instance = this;
  }


  /**
   * Inner static class representing individual vendor configurations.
   *
   * <p>Each vendor configuration contains its base URL, brand mappings, category mappings, and a
   * map for universal paths to all products.
   * </p>
   */
  @Getter
  @Setter
  public static class Vendor {

    private String baseUrl;
    private Map<String, String> brands;
    private Map<String, String> categories;
    private Map<String, String> all;
  }
}
