package com.shuttleverse.aggregator.model;

import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Vendor;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents badminton shuttle products.
 */
@Getter
@Setter
@Document(collection = "shuttles")
public class Shuttle extends BadmintonProduct {

  private Shuttle(String productId, String name, Brand brand, Vendor vendor,
      String vendorUrl, List<Variant> variants, List<String> imageSources) {
    super(productId, name, brand, vendor, vendorUrl, variants, imageSources);
  }

  /**
   * Returns a new ShuttleBuilder instance to create a Shuttle object.
   *
   * @return A new ShuttleBuilder instance
   */
  public static ShuttleBuilder builder() {
    return new ShuttleBuilder();
  }

  /**
   * Builder class for creating Shuttle instances.
   *
   * <p>This builder extends AbstractProductBuilder to provide a fluent API for constructing
   * Shuttle objects with required fields.
   */
  public static class ShuttleBuilder extends AbstractProductBuilder<Shuttle, ShuttleBuilder> {

    private ShuttleBuilder() {
    }

    @Override
    protected ShuttleBuilder self() {
      return this;
    }

    @Override
    public Shuttle build() {
      return new Shuttle(getProductId(), getName(), getBrand(), getVendor(), getVendorUrl(),
          getVariants(), getImageSources());
    }

  }

}
