package com.shuttleverse.aggregator.model;

import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Vendor;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public abstract class Product {
  @Id
  private String id;
  private String productId;
  private String name;
  private Brand brand;
  private Vendor vendor;
  private String vendorUrl;
  private List<Variant> variants;
  private List<String> imageSources;
}
