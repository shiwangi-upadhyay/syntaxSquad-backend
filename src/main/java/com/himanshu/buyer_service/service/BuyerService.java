package com.himanshu.buyer_service.service;

import com.himanshu.buyer_service.feign.SellerClient;
import com.himanshu.buyer_service.model.BuyerOrder;
import com.himanshu.buyer_service.model.Product;
import com.himanshu.buyer_service.model.SellerOrder;
import com.himanshu.buyer_service.repository.BuyerOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyerService {

    @Autowired
    private SellerClient sellerClient;

    @Autowired
    private BuyerOrderRepository orderRepo;

    public List<Product> browseProducts() {
        return sellerClient.getProducts();
    }

    public BuyerOrder placeOrder(Long productId, int qty, String email) {

        boolean available = sellerClient.checkAvailability(productId, qty);
        if (!available) {
            throw new RuntimeException("Product not available");
        }

        SellerOrder sellerOrder =
                sellerClient.createOrder(productId, qty, email);

        BuyerOrder order = new BuyerOrder();
        order.setProductId(productId);
        order.setQuantity(qty);
        order.setStatus("CONFIRMED");
        order.setTotalAmount(qty * 100); // sample price

        return orderRepo.save(order);
    }

    public List<BuyerOrder> trackOrders() {
        return orderRepo.findAll();
    }

    public double totalAmount() {
        return orderRepo.findAll()
                .stream()
                .mapToDouble(BuyerOrder::getTotalAmount)
                .sum();
    }
}