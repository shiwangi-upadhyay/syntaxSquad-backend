package com.himanshu.buyer_service.feign;

import com.himanshu.buyer_service.model.Product;
import com.himanshu.buyer_service.model.SellerOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "seller-service")
public interface SellerClient {

    @GetMapping("/seller/products")
    List<Product> getProducts();

    @GetMapping("/seller/check")
    boolean checkAvailability(@RequestParam Long productId,
                              @RequestParam int qty);

    @PostMapping("/seller/order")
    SellerOrder createOrder(@RequestParam Long productId,
                            @RequestParam int qty,
                            @RequestParam String email);
}