package com.shuttleverse.aggregator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin
public interface ProductController {
  ResponseEntity<?> getProducts(@RequestParam(required = false) List<String> brands,
                                @RequestParam(required = false) List<String> categories,
                                @RequestParam(required = false) Double priceMin,
                                @RequestParam(required = false) Double priceMax,
                                @RequestParam(defaultValue = "10") Integer limit);
}
