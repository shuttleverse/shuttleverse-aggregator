package com.shuttleverse.aggregator.utils;

import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct;
import com.shuttleverse.aggregator.api.model.ApiBadmintonProduct.Image;
import com.shuttleverse.aggregator.api.model.ApiVariant;
import com.shuttleverse.aggregator.enums.Brand;
import com.shuttleverse.aggregator.enums.Category;
import com.shuttleverse.aggregator.enums.Currency;
import com.shuttleverse.aggregator.enums.Vendor;
import com.shuttleverse.aggregator.model.Product;
import com.shuttleverse.aggregator.model.Shoe;
import com.shuttleverse.aggregator.model.Shuttle;
import com.shuttleverse.aggregator.model.Variant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class ApiModelConverterTests {

  @Mock
  private ApiBadmintonProduct mockApiProduct;

  @Mock
  private ApiVariant mockVariant;

  @Mock
  private Image mockImage;

  @BeforeEach
  void setUp() {
    mockApiProduct = mock(ApiBadmintonProduct.class);
    mockVariant = mock(ApiVariant.class);
    mockImage = mock(Image.class);
  }

  @Test
  void testConvertNonYumoVendor() {
    ApiModelConverter<Shuttle> converter = new ApiModelConverter<>(Shuttle.builder());
    String productId = "prod123";
    String title = "Badminton Racket Pro";
    String brandName = "Yonex";
    Double price = 100.00;
    String variantTitle = "Red";
    String imageUrl = "https://example.com/image.jpg";
    String vendorUrl = "https://vendor.com/product";

    when(mockApiProduct.getProductId()).thenReturn(productId);
    when(mockApiProduct.getTitle()).thenReturn(title);
    when(mockApiProduct.getBrand()).thenReturn(brandName);
    when(mockVariant.getTitle()).thenReturn(variantTitle);
    when(mockVariant.getPrice()).thenReturn(price);
    when(mockApiProduct.getVariants()).thenReturn(Collections.singletonList(mockVariant));
    when(mockImage.src()).thenReturn(imageUrl);
    when(mockApiProduct.getImages()).thenReturn(Collections.singletonList(mockImage));
    when(mockApiProduct.getVendorUrl(Vendor.BADMINTON_AVENUE, Category.SHUTTLE))
        .thenReturn(vendorUrl);

    Product result = converter.apply(mockApiProduct, Vendor.BADMINTON_AVENUE, Category.SHUTTLE);

    assertNotNull(result);
    assertEquals(productId, result.getProductId());
    assertEquals(title, result.getName());
    assertEquals(Brand.fromString(brandName), result.getBrand());
    assertEquals(Vendor.BADMINTON_AVENUE, result.getVendor());
    assertEquals(vendorUrl, result.getVendorUrl());

    List<Variant> resultVariants = result.getVariants();
    assertEquals(1, resultVariants.size());
    assertEquals(variantTitle, resultVariants.get(0).getTitle());
    assertEquals(price, resultVariants.get(0).getPrice());

    List<String> resultImageSources = result.getImageSources();
    assertEquals(1, resultImageSources.size());
    assertEquals(imageUrl, resultImageSources.get(0));
  }

  @Test
  void testConvertYumoVendor() {
    ApiModelConverter<Shuttle> converter = new ApiModelConverter<>(Shuttle.builder());
    // Given
    String productId = "prod456";
    String title = "Shuttlecock Pro";
    String brandName = "Victor";
    double price = 50.00;
    Double convertedPrice = PriceConverter.convert(Currency.CAD, price);
    String variantTitle = "12-Pack";
    String imageUrl = "https://example.com/shuttlecock.jpg";
    String vendorUrl = "https://yumo.com/product";

    when(mockApiProduct.getProductId()).thenReturn(productId);
    when(mockApiProduct.getTitle()).thenReturn(title);
    when(mockApiProduct.getBrand()).thenReturn(brandName);
    when(mockVariant.getTitle()).thenReturn(variantTitle);
    when(mockVariant.getPrice()).thenReturn(price);
    when(mockApiProduct.getVariants()).thenReturn(Collections.singletonList(mockVariant));
    when(mockImage.src()).thenReturn(imageUrl);
    when(mockApiProduct.getImages()).thenReturn(Collections.singletonList(mockImage));
    when(mockApiProduct.getVendorUrl(Vendor.YUMO, Category.SHUTTLE))
        .thenReturn(vendorUrl);

    Product result = converter.apply(mockApiProduct, Vendor.YUMO, Category.SHUTTLE);

    assertNotNull(result);
    assertEquals(productId, result.getProductId());
    assertEquals(title, result.getName());
    assertEquals(Brand.fromString(brandName), result.getBrand());
    assertEquals(Vendor.YUMO, result.getVendor());
    assertEquals(vendorUrl, result.getVendorUrl());

    List<Variant> resultVariants = result.getVariants();
    assertEquals(1, resultVariants.size());
    assertEquals(variantTitle, resultVariants.get(0).getTitle());
    assertEquals(convertedPrice, resultVariants.get(0).getPrice());

    List<String> resultImageSources = result.getImageSources();
    assertEquals(1, resultImageSources.size());
    assertEquals(imageUrl, resultImageSources.get(0));
  }

  @Test
  void testYumoProductPriceGetsConverted() {
    ApiModelConverter<Shoe> converter = new ApiModelConverter<>(Shoe.builder());

    // Given
    String productId = "prod789";
    String title = "Badminton Court Shoes";
    String brandName = "LiNing";

    ApiVariant variant1 = mock(ApiVariant.class);
    when(variant1.getTitle()).thenReturn("Size 8");
    when(variant1.getPrice()).thenReturn(120.00);

    ApiVariant variant2 = mock(ApiVariant.class);
    when(variant2.getTitle()).thenReturn("Size 9");
    when(variant2.getPrice()).thenReturn(120.00);

    Image image1 = mock(Image.class);
    when(image1.src()).thenReturn("https://example.com/shoes_front.jpg");

    Image image2 = mock(Image.class);
    when(image2.src()).thenReturn("https://example.com/shoes_side.jpg");

    String vendorUrl = "https://vendor.com/shoes";

    when(mockApiProduct.getProductId()).thenReturn(productId);
    when(mockApiProduct.getTitle()).thenReturn(title);
    when(mockApiProduct.getBrand()).thenReturn(brandName);
    when(mockApiProduct.getVariants()).thenReturn(Arrays.asList(variant1, variant2));
    when(mockApiProduct.getImages()).thenReturn(Arrays.asList(image1, image2));
    when(mockApiProduct.getVendorUrl(Vendor.YUMO, Category.SHOE))
        .thenReturn(vendorUrl);

    Product result = converter.apply(mockApiProduct, Vendor.YUMO, Category.SHOE);

    assertNotNull(result);
    assertEquals(productId, result.getProductId());
    assertEquals(title, result.getName());
    assertEquals(Brand.fromString(brandName), result.getBrand());

    List<Variant> resultVariants = result.getVariants();
    assertEquals(2, resultVariants.size());
    assertEquals("Size 8", resultVariants.get(0).getTitle());
    assertEquals(PriceConverter.convert(Currency.CAD, 120.00),
        resultVariants.get(0).getPrice());
    assertEquals("Size 9", resultVariants.get(1).getTitle());
    assertNotEquals(Double.valueOf(120.00), resultVariants.get(1).getPrice());

    List<String> resultImageSources = result.getImageSources();
    assertEquals(2, resultImageSources.size());
    assertEquals("https://example.com/shoes_front.jpg", resultImageSources.get(0));
    assertEquals("https://example.com/shoes_side.jpg", resultImageSources.get(1));
  }

  private <T> T mock(Class<T> classToMock) {
    return org.mockito.Mockito.mock(classToMock);
  }
}