package com.shuttleverse.aggregator.model;

import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Vendor;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents badminton racket products.
 */
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "rackets")
public class Racket extends BadmintonProduct {

  private Racket(String productId, String name, Brand brand, Vendor vendor, String vendorUrl,
      List<Variant> variants, List<String> imageSources) {
    super(productId, name, brand, vendor, vendorUrl, variants, imageSources);
  }

  /**
   * Returns a new RacketBuilder instance to create a Racket object.
   *
   * @return A new RacketBuilder instance
   */
  public static RacketBuilder builder() {
    return new RacketBuilder();
  }

  /**
   * Builder class for creating Racket instances.
   *
   * <p>This builder extends AbstractProductBuilder to provide a fluent API for constructing Racket
   * objects with required fields.
   */
  public static class RacketBuilder extends
      AbstractProductBuilder<Racket, RacketBuilder> {

    private RacketBuilder() {
    }

    @Override
    protected RacketBuilder self() {
      return this;
    }

    @Override
    public Racket build() {
      return new Racket(getProductId(), getName(), getBrand(), getVendor(), getVendorUrl(),
          getVariants(), getImageSources());
    }

  }
}
