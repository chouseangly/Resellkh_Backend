package com.example.backendresellkh.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Long notificationId;
    private Long userId;
    private String message;
    private boolean isRead = false;
    private LocalDateTime createdAt;
}
