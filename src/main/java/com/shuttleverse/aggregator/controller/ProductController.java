package com.shuttleverse.aggregator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin
public interface ProductController {
  ResponseEntity<?> getProducts(@RequestParam(required = false) String brand,
                                @RequestParam(required = false) String category,
                                @RequestParam(required = false) Double price);
}
