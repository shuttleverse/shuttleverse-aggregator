package com.shuttleverse.aggregator.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Application configuration class for setting up global beans and properties.
 *
 * <p>Enables configuration properties for {@link VendorConfig} and defines a custom
 * {@link WebClient} bean with increased memory limits for handling large API responses.
 * </p>
 */
@Configuration
@EnableConfigurationProperties(VendorConfig.class)
public class AppConfig {

  /**
   * Configures and provides a {@link WebClient} bean with customized exchange strategies.
   *
   * <p>The {@link WebClient} is set with a 10MB in-memory size limit
   * </p>
   *
   * @param builder the {@link WebClient.Builder} provided by Spring Boot autoconfiguration
   * @return a fully configured {@link WebClient} instance
   */
  @Bean
  public WebClient webClient(WebClient.Builder builder) {
    return builder
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer.defaultCodecs()
                .maxInMemorySize(10 * 1024 * 1024))
            .build())
        .build();
  }

}
