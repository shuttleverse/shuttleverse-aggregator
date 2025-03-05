package com.shuttleverse.aggregator.service;

import com.shuttleverse.aggregator.api.model.ApiProduct;
import com.shuttleverse.aggregator.enums.Vendor;

import java.util.List;

public interface ProductService<T extends ApiProduct> {
  void updateProductInformation(List<T> products, Vendor vendor);
}
