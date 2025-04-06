package com.shuttleverse.aggregator.model;

import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Vendor;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents badminton shoe products.
 */
@NoArgsConstructor
@Getter
@Setter
@Document
public class Shoe extends BadmintonProduct {

  private Shoe(String productId, String name, Brand brand, Vendor vendor, String vendorUrl,
      List<Variant> variants, List<String> imageSources) {
    super(productId, name, brand, vendor, vendorUrl, variants, imageSources);
  }

  /**
   * Returns a new ShoeBuilder instance to create a Shoe object.
   *
   * @return A new ShoeBuilder instance
   */
  public static ShoeBuilder builder() {
    return new ShoeBuilder();
  }

  /**
   * Builder class for creating Shoe instances.
   *
   * <p>This builder extends AbstractProductBuilder to provide a fluent API for constructing Shoe
   * objects with required fields.
   */
  public static class ShoeBuilder extends
      AbstractProductBuilder<Shoe, ShoeBuilder> {

    private ShoeBuilder() {
    }

    @Override
    protected ShoeBuilder self() {
      return this;
    }

    @Override
    public Shoe build() {
      return new Shoe(getProductId(), getName(), getBrand(), getVendor(), getVendorUrl(),
          getVariants(), getImageSources());
    }

  }
}
