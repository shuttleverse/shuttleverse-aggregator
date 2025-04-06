package com.shuttleverse.aggregator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents badminton accessory products.
 */
@NoArgsConstructor
@Getter
@Setter
@Document
public class Accessories extends BadmintonProduct {

}
