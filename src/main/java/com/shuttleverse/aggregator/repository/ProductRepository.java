package com.shuttleverse.aggregator.repository;

import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository<T extends Product> extends MongoRepository<T, UUID> {
  Optional<T> findByNameAndVendor(String name, Vendor vendor);
}
