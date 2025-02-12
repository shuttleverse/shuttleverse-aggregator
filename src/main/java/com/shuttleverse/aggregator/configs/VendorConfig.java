package com.shuttleverse.aggregator.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "vendors")
@Getter
@Setter
public class VendorConfig {
  private Map<String, Vendor> vendors;

  @PostConstruct
  public void init() {
    System.out.println("Vendors loaded: " + vendors);
  }

  @Getter
  @Setter
  public static class Vendor {
    private String baseUrl;
    private Map<String, String> brands;
    private Map<String, String> categories;
  }
}
