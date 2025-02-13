package com.shuttleverse.aggregator.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ApiProduct {
  @NonNull
  String title;
  @NonNull
  String vendor; // the brand of the product
  @NonNull
  List<ApiVariant> variants;
  @NonNull
  List<Image> images;

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record Image(String src) {
  }
}