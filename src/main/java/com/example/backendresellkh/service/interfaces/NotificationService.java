package com.example.backendresellkh.service.interfaces;


import com.example.backendresellkh.dto.request.NotificationRequest;
import com.example.backendresellkh.dto.response.NotificationResponse;

import java.util.List;

public interface NotificationService {
    NotificationResponse createNotification(NotificationRequest notificationRequest);
    List<NotificationResponse> getAllNotifications();
}
