package com.shuttleverse.aggregator.mapper;

import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct;
import com.shuttleverse.aggregator.api.model.ApiRacket;
import com.shuttleverse.aggregator.api.model.ApiShoe;
import com.shuttleverse.aggregator.api.model.ApiShuttle;
import com.shuttleverse.aggregator.enums.Category;
import java.util.HashMap;
import java.util.Map;

/**
 * Mapper class to map a {@link Category} to the corresponding API response type.
 */
public class CategoryResponseMapper {

  private static final Map<Category, Class<? extends ApiBadmintonProduct>>
      CATEGORY_TO_RESPONSE_TYPE = new HashMap<>();

  static {
    CATEGORY_TO_RESPONSE_TYPE.put(Category.RACKET, ApiRacket.class);
    CATEGORY_TO_RESPONSE_TYPE.put(Category.SHUTTLE, ApiShuttle.class);
    CATEGORY_TO_RESPONSE_TYPE.put(Category.SHOE, ApiShoe.class);
  }

  /**
   * Retrieves the corresponding API response class for a given {@link Category}.
   *
   * @param category the {@link Category} for which the response class is needed
   * @return the corresponding response class for the category, or {@code null} if not found
   */
  public static Class<? extends ApiBadmintonProduct> getResponseType(Category category) {
    return CATEGORY_TO_RESPONSE_TYPE.get(category);
  }
}