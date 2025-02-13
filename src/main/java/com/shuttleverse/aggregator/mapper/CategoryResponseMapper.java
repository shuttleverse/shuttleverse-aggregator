package com.shuttleverse.aggregator.mapper;

import com.shuttleverse.aggregator.api.model.ApiProduct;
import com.shuttleverse.aggregator.api.model.ApiRacket;
import com.shuttleverse.aggregator.api.model.ApiShoe;
import com.shuttleverse.aggregator.enums.Category;

import java.util.HashMap;
import java.util.Map;

public class CategoryResponseMapper {
  private static final Map<Category, Class<? extends ApiProduct>> CATEGORY_TO_RESPONSE_TYPE = new HashMap<>();

  static {
    CATEGORY_TO_RESPONSE_TYPE.put(Category.RACKET, ApiRacket.class);
    CATEGORY_TO_RESPONSE_TYPE.put(Category.SHOE, ApiShoe.class);
  }

  public static Class<? extends ApiProduct> getResponseType(Category category) {
    return CATEGORY_TO_RESPONSE_TYPE.get(category);
  }
}