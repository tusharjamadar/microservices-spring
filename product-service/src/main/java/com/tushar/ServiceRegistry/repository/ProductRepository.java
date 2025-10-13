package com.tushar.ServiceRegistry.repository;

import com.tushar.ServiceRegistry.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
