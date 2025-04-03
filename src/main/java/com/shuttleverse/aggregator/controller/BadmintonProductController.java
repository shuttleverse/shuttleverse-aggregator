package com.shuttleverse.aggregator.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aggregator")
public class BadmintonProductController implements ProductController {

  @Override
  @GetMapping("/test")
  public ResponseEntity<?> getProducts(List<String> brands, List<String> categories,
      Double priceMin, Double priceMax, Integer limit) {
    return new ResponseEntity<>("test succeeded", HttpStatus.OK);
  }
}
