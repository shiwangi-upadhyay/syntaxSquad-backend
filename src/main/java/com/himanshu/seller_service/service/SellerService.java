package com.himanshu.seller_service.service;

import com.himanshu.seller_service.model.Product;
import com.himanshu.seller_service.model.SellerOrder;
import com.himanshu.seller_service.repository.ProductRepository;
import com.himanshu.seller_service.repository.SellerOrderRepository;
import com.netflix.discovery.converters.Auto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private SellerOrderRepository orderRepo;
    private JavaMailSender mailSender;

    public Product addProduct(Product p) {
        return productRepo.save(p);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public boolean checkAvailability(Long productId, int qty) {
        Product p = productRepo.findById(productId).orElseThrow();
        return p.getQuantity() >= qty;
    }

    public SellerOrder createOrder(Long productId, int qty, String email) {
        Product p = productRepo.findById(productId).orElseThrow();

        if (p.getQuantity() < qty) {
            throw new RuntimeException("Out of stock");
        }

        p.setQuantity(p.getQuantity() - qty);
        productRepo.save(p);

        SellerOrder order = new SellerOrder();
        order.setProductId(productId);
        order.setQuantity(qty);
        order.setBuyerEmail(email);
        order.setStatus("CONFIRMED");

        orderRepo.save(order);
        return order;
    }


    public List<SellerOrder> allOrders() {
        return orderRepo.findAll();
    }

    public double totalSales() {
        return orderRepo.findAll()
                .stream()
                .mapToDouble(o -> o.getQuantity())
                .sum();
    }
}