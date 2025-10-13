package com.tushar.ServiceRegistry.controller;

import com.tushar.ServiceRegistry.dto.ProductRequest;
import com.tushar.ServiceRegistry.dto.ProductResponse;
import com.tushar.ServiceRegistry.exception.ProductServiceCustomException;
import com.tushar.ServiceRegistry.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
        Long productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        List<ProductResponse> productList = productService.getAllProducts();

        return ResponseEntity.ok(productList);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        ProductResponse product = productService.getProductById(id);

        if(product == null)throw new ProductServiceCustomException("Product Not Found with Id: "+ id, "PRODUCT_NOT_FOUND");

        return ResponseEntity.ok(product);
    }
}
