package com.shuttleverse.aggregator.model;

import com.shuttleverse.aggregator.builder.ProductBuilder;
import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Vendor;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * Represents an abstract badminton product.
 */
@NoArgsConstructor
@Getter
@Setter
public abstract class BadmintonProduct implements Product {

  @Id
  protected String id;
  protected String productId;
  protected String name;
  protected Brand brand;
  protected Vendor vendor;
  protected String vendorUrl;
  protected List<Variant> variants;
  protected List<String> imageSources;

  /**
   * Creates a new BadmintonProduct with the specified properties.
   *
   * @param productId    The unique identifier for this product
   * @param name         The name/title of the product
   * @param brand        The brand of the product
   * @param vendor       The vendor selling the product
   * @param vendorUrl    The URL to the product page on the vendor's website
   * @param variants     The list of available variants for this product
   * @param imageSources The list of image URLs for this product
   */
  public BadmintonProduct(String productId, String name, Brand brand, Vendor vendor,
      String vendorUrl, List<Variant> variants, List<String> imageSources) {
    this.productId = productId;
    this.name = name;
    this.brand = brand;
    this.vendor = vendor;
    this.vendorUrl = vendorUrl;
    this.variants = variants;
    this.imageSources = imageSources;
  }

  /**
   * Abstract builder class for creating BadmintonProduct instances.
   *
   * <p>This class implements the {@link ProductBuilder} interface and provides a common
   * implementation of builder methods for all badminton product types. Concrete subclasses must
   * implement the {@link #self()} and {@link #build()} methods.
   *
   * <p>The builder follows the curiously recurring template pattern (CRTP) to enable method
   * chaining while preserving type information in derived builders.
   *
   * @param <M> The type of product being built, must extend {@link Product}
   * @param <B> The type of builder, must extend {@link AbstractProductBuilder}
   */
  @Getter
  protected abstract static class AbstractProductBuilder<M extends Product,
      B extends AbstractProductBuilder<M, B>>
      implements ProductBuilder<M, B> {

    private String productId;
    private String name;
    private Brand brand;
    private Vendor vendor;
    private String vendorUrl;
    private List<Variant> variants;
    private List<String> imageSources;

    public B productId(String productId) {
      this.productId = productId;
      return self();
    }

    public B name(String name) {
      this.name = name;
      return self();
    }

    public B brand(Brand brand) {
      this.brand = brand;
      return self();
    }

    public B vendor(Vendor vendor) {
      this.vendor = vendor;
      return self();
    }

    public B vendorUrl(String vendorUrl) {
      this.vendorUrl = vendorUrl;
      return self();
    }

    public B variants(List<Variant> variants) {
      this.variants = variants;
      return self();
    }

    public B imageSources(List<String> imageSources) {
      this.imageSources = imageSources;
      return self();
    }

    protected abstract B self();

    public abstract M build();

  }
}
