package com.booknest.services;

import com.booknest.entities.Delivery;
import com.booknest.entities.Order;
import com.booknest.repositories.DeliveryRepository;
import com.booknest.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;

    public Delivery createDelivery(Long orderId, Delivery delivery) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        delivery.setOrder(order);

        if (delivery.getTrackingNumber() == null ||
                delivery.getTrackingNumber().isBlank()) {

            delivery.setTrackingNumber(
                    "TRK-" + UUID.randomUUID()
                            .toString()
                            .substring(0, 8)
                            .toUpperCase()
            );
        }

        return deliveryRepository.save(delivery);
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(Long id) {

        return deliveryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Delivery not found"));
    }

    public Delivery getByTrackingNumber(
            String trackingNumber) {

        return deliveryRepository
                .findByTrackingNumber(trackingNumber)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Tracking number not found"));
    }

    public Delivery updateStatus(
            Long id,
            Delivery deliveryRequest) {

        Delivery delivery = getDeliveryById(id);

        delivery.setStatus(
                deliveryRequest.getStatus());

        delivery.setNotes(
                deliveryRequest.getNotes());

        return deliveryRepository.save(delivery);
    }

    public void deleteDelivery(Long id) {
        deliveryRepository.deleteById(id);
    }
}