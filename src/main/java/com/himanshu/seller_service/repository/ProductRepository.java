package com.himanshu.seller_service.repository;

import com.himanshu.seller_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
