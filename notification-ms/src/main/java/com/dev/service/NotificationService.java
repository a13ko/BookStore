package com.dev.service;

import com.dev.dto.OrderEvent;
import com.dev.entity.Notification;

import java.util.Optional;
import java.util.UUID;

public interface NotificationService {
    Notification saveNotification(OrderEvent orderEvent);
    void sendNotification(UUID id);
    Optional<Notification> findById(UUID id);
}
