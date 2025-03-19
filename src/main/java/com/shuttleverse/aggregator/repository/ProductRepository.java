package com.shuttleverse.aggregator.repository;

import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.BadmintonProduct;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing badminton products in MongoDB.
 *
 * @param <T> the type of {@link BadmintonProduct}
 */
@Repository
public interface ProductRepository<T extends BadmintonProduct> extends MongoRepository<T, String> {

  /**
   * Finds a badminton product by its product ID.
   *
   * @param productId the product ID to search for
   * @return an {@link Optional} containing the product if found, or an empty {@link Optional} if
   *     not.
   */
  Optional<T> findByProductId(String productId);

  /**
   * Finds a badminton product by its vendor.
   *
   * @param vendor the vendor to search for
   * @return an {@link Optional} containing the product if found, or an empty {@link Optional} if
   *     not.
   */
  Optional<T> findByVendor(Vendor vendor);

  /**
   * Deletes products from the specified vendor, except for the ones with product IDs in the
   * provided list.
   *
   * @param vendor     the vendor whose products are to be deleted
   * @param productIds the list of product IDs to exclude from deletion
   */
  void deleteByVendorAndProductIdNotIn(Vendor vendor, List<String> productIds);
}
