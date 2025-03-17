package com.shuttleverse.aggregator.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a product variant fetched from a vendor API.
 *
 * <p>Each variant includes details such as title and price, and is used as part of a broader
 * product representation.
 * </p>
 *
 * <p>This class uses Lombok annotations for generating getter and setter methods and is annotated
 * to ignore unknown JSON properties during deserialization.
 * </p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ApiVariant {

  private String title;
  private Double price;
}
