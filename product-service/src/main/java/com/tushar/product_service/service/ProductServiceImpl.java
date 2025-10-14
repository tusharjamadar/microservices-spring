package com.tushar.product_service.service;

import com.tushar.product_service.dto.ProductRequest;
import com.tushar.product_service.dto.ProductResponse;
import com.tushar.product_service.entity.Product;
import com.tushar.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    @Override
    public Long addProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .productName(productRequest.getName())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();

        productRepository.save(product);

        return product.getProductId();
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> {
            return ProductResponse.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product == null)return null;

        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }
}
