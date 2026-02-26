package com.himanshu.buyer_service.controller;

import com.himanshu.buyer_service.model.BuyerOrder;
import com.himanshu.buyer_service.model.Product;
import com.himanshu.buyer_service.service.BuyerService;
import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyer")
@RequiredArgsConstructor
public class BuyerController {

    @Autowired
    private BuyerService service;

    @GetMapping("/products")
    public List<Product> browse() {
        return service.browseProducts();
    }

    @PostMapping("/order")
    public BuyerOrder order(@RequestParam Long productId,
                            @RequestParam int qty,
                            @RequestParam String email) {
        return service.placeOrder(productId, qty, email);
    }

    @GetMapping("/orders")
    public List<BuyerOrder> track() {
        return service.trackOrders();
    }

    @GetMapping("/total")
    public double total() {
        return service.totalAmount();
    }
}