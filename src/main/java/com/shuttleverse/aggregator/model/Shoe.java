package com.shuttleverse.aggregator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents badminton shoe products.
 */
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Document
public class Shoe extends BadmintonProduct {

}
