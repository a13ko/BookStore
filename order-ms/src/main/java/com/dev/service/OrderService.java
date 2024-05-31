package com.dev.service;

import com.dev.entity.Order;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Order createOrder(Order order);
    Order confirmOrder(UUID orderId);
    Order getOrderById(UUID id);
}
