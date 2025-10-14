package com.tushar.product_service.service;

import com.tushar.product_service.dto.ProductRequest;
import com.tushar.product_service.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    Long addProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);
}
