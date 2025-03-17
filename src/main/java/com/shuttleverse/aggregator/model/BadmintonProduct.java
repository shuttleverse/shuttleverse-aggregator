package com.shuttleverse.aggregator.model;

import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Vendor;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

/**
 * Represents an abstract badminton product.
 */
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public abstract class BadmintonProduct implements Product {

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
