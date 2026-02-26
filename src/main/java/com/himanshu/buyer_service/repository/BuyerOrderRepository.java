package com.himanshu.buyer_service.repository;

import com.himanshu.buyer_service.model.BuyerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerOrderRepository extends JpaRepository<BuyerOrder, Long> {
}
