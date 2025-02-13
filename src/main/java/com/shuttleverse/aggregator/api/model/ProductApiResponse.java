package com.shuttleverse.aggregator.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductApiResponse<T extends ApiProduct>(List<T> products) {
}


