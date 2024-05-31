package com.dev.service.impl;

import com.dev.dto.OrderEvent;
import com.dev.entity.Notification;
import com.dev.repository.NotificationRepository;
import com.dev.service.NotificationService;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification saveNotification(OrderEvent orderEvent) {
        var notification = Notification.builder()
                .orderId(orderEvent.getOrderId())
                .userId(orderEvent.getUserId())
                .userEmail(orderEvent.getUserEmail())
                .message("Order " + orderEvent.getOrderId() + " has been confirmed.")
                .build();
        return notificationRepository.save(notification);
    }

    @Override
    public void sendNotification(UUID id) {
        Optional<Notification> notificationOpt = notificationRepository.findById(id);
        if (notificationOpt.isPresent()) {
            Notification notification = notificationOpt.get();
            log.info("Sending notification to {}: {}", notification.getUserEmail(), notification.getMessage());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
    }

    @KafkaListener(topics = "order_topic", groupId = "notification-group")
    private void consumeOrderEvent(OrderEvent orderEvent) {
        log.info("Received order event: {}", orderEvent);
        saveNotification(orderEvent);
    }

    @Override
    public Optional<Notification> findById(UUID id) {
        return notificationRepository.findById(id);
    }
}
