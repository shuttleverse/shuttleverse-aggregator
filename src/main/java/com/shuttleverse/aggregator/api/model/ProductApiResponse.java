package com.shuttleverse.aggregator.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Product;
import java.util.List;

/**
 * A generic API response wrapper for product data fetched from badminton {@link Vendor}.
 *
 * @param <T>      the type of product, extending {@link ApiBadmintonProduct}
 * @param products the list of products returned by the API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductApiResponse<T extends ApiBadmintonProduct>(
    List<T> products) {

}
