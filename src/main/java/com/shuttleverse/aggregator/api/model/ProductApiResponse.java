package com.shuttleverse.aggregator.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shuttleverse.aggregator.model.BadmintonProduct;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductApiResponse<T extends ApiBadmintonProduct>(
    List<T> products) {
}


