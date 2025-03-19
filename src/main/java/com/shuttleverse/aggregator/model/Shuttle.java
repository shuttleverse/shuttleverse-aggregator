package com.shuttleverse.aggregator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents badminton shuttle products.
 */
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Document(collection = "shuttles")
public class Shuttle extends BadmintonProduct {

}
