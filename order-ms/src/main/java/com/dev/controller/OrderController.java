package com.dev.controller;

import com.dev.entity.Order;
import com.dev.service.OrderService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order, @RequestHeader("X-USER-ID") UUID userId,
                                             @RequestHeader("X-USER-EMAIL") String userEmail) {
        order.setUserId(userId);
        order.setUserEmail(userEmail);
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<Order> confirmOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.confirmOrder(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
