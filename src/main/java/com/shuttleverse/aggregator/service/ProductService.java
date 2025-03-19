package com.shuttleverse.aggregator.service;

import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct;
import com.shuttleverse.aggregator.enums.Vendor;
import java.util.List;

/**
 * Service interface for managing badminton products.
 */
public interface ProductService<T extends ApiBadmintonProduct> {

  /**
   * <li> Updates the database with the given list of products and the given vendor that sells the
   * product.
   * </li>
   *
   * <li> Deletes the products that no longer exists in the database.
   * </li>
   *
   * <li> Appends the new price history of the given list of products.
   * </li>
   *
   * @param products the list of products
   * @param vendor   the vendor
   */
  void updateProductInformation(List<T> products, Vendor vendor);
}
