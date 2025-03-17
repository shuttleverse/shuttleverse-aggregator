package com.shuttleverse.aggregator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a variant of a product, including its title and price.
 */
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Variant {

  private String title;
  private Double price;
}
