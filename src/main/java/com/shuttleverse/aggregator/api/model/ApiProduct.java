package com.shuttleverse.aggregator.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ApiProduct {
  String title;
  String vendor; // the brand of the product
  List<ApiVariant> variants;
  List<Image> images;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record Image(String src) {
  }
}