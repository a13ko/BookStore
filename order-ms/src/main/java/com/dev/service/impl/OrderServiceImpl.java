package com.dev.service.impl;

import com.dev.client.BookClient;
import com.dev.dto.OrderEvent;
import com.dev.entity.Order;
import com.dev.repository.OrderRepository;
import com.dev.service.OrderService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final KafkaProducerService kafkaProducerService;
    private final BookClient bookClient;

    @Override
    public Order createOrder(Order order) {
        var book = bookClient.getBookById(order.getBookId()).getBody();
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        return orderRepository.save(order);
    }

    @Override
    public Order confirmOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        var book = bookClient.getBookById(order.getBookId());
        log.info("book details is {}", book);
        if (book != null) {
            OrderEvent orderEvent = new OrderEvent();
            orderEvent.setOrderId(order.getId());
            orderEvent.setUserId(order.getUserId());
            orderEvent.setUserEmail(order.getUserEmail());

            kafkaProducerService.sendMessage(orderEvent);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        return order;
    }

    @Override
    public Order getOrderById(UUID id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }
}
