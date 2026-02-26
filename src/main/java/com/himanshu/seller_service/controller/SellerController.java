package com.himanshu.seller_service.controller;

import com.himanshu.seller_service.model.Product;
import com.himanshu.seller_service.model.SellerOrder;
import com.himanshu.seller_service.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {

    @Autowired
    private SellerService service;

    @PostMapping("/product")
    public Product addProduct(@RequestBody Product p) {
        return service.addProduct(p);
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/check")
    public boolean check(@RequestParam Long productId,
                         @RequestParam int qty) {
        return service.checkAvailability(productId, qty);
    }

    @PostMapping("/order")
    public SellerOrder createOrder(@RequestParam Long productId,
                                   @RequestParam int qty,
                                   @RequestParam String email) {
        return service.createOrder(productId, qty, email);
    }
}