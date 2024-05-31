package com.dev.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class OrderEvent {
    private UUID orderId;
    private UUID userId;
    private String userEmail;
}
