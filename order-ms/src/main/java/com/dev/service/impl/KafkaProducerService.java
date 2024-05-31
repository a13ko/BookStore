package com.dev.service.impl;

import com.dev.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private static final String TOPIC = "order_topic";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(OrderEvent orderEvent) {
        log.info("Publishing order event: {}", orderEvent);
        kafkaTemplate.send(TOPIC, orderEvent);
    }
}
