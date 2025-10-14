package com.tushar.order_service.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductService {
    @PutMapping(path = "/products/reduceQuantity/{id}")
    ResponseEntity<Void> reduceQuantity(
            @PathVariable(name = "id") Long productId, @RequestParam Long quantity
    );
}
