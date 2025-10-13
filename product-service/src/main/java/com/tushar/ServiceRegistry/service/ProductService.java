package com.tushar.ServiceRegistry.service;

import com.tushar.ServiceRegistry.dto.ProductRequest;
import com.tushar.ServiceRegistry.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    Long addProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);
}
