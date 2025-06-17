package com.example.backendresellkh.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationRequest {
    private Long userId;
    private String message;
}