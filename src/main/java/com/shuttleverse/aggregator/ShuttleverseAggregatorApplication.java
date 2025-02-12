package com.shuttleverse.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ShuttleverseAggregatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShuttleverseAggregatorApplication.class, args);
  }

}
