package com.shuttleverse.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * The main entry point of the ShuttleVerse Aggregator application.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class ShuttleverseAggregatorApplication {

  /**
   * The main method to run the ShuttleVerse Aggregator application.
   *
   * @param args the command-line arguments passed to the application
   */
  public static void main(String[] args) {
    SpringApplication.run(ShuttleverseAggregatorApplication.class, args);
  }

}
