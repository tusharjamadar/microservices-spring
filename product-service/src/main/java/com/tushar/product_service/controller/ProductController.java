package com.tushar.product_service.controller;

import com.tushar.product_service.dto.ProductRequest;
import com.tushar.product_service.dto.ProductResponse;
import com.tushar.product_service.exception.ProductServiceCustomException;
import com.tushar.product_service.service.ProductService;
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

    @PutMapping(path = "/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable(name = "id") Long productId, @RequestParam Long quantity){
        productService.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
