package com.himanshu.seller_service.repository;

import com.himanshu.seller_service.model.SellerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerOrderRepository extends JpaRepository<SellerOrder, Long> {
}
