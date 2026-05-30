package com.booknest.services;

import com.booknest.entities.Customer;
import com.booknest.entities.Order;
import com.booknest.repositories.CustomerRepository;
import com.booknest.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public Order createOrder(
            Long customerId,
            BigDecimal totalAmount) {

        Customer customer =
                customerRepository.findById(customerId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Customer not found"));

        Order order = Order.builder()
                .customer(customer)
                .totalAmount(totalAmount)
                .build();

        return orderRepository.save(order);
    }

    public List<Order> getCustomerOrders(
            Long customerId) {

        return orderRepository
                .findByCustomerId(customerId);
    }

    public Order getOrder(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"));
    }
}