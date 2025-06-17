package com.example.backendresellkh.service.impl;

import com.example.backendresellkh.dto.request.NotificationRequest;
import com.example.backendresellkh.dto.response.NotificationResponse;
import com.example.backendresellkh.model.entity.Notification;
import com.example.backendresellkh.repository.NotificationRepository;
import com.example.backendresellkh.service.interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public NotificationResponse createNotification(NotificationRequest notificationRequest){
        Notification notification = Notification.builder()
                .userId(notificationRequest.getUserId())
                .message(notificationRequest.getMessage())
                .createdAt(LocalDateTime.now())
                .build();
        notificationRepository.createNotification(notification);
        return maptoNotificationResponse(notification);
    }

    @Override
    public List<NotificationResponse> getAllNotifications(){
        return notificationRepository.findAll()
                .stream()
                .map(this::maptoNotificationResponse)
                .collect(Collectors.toList());
    }

    private NotificationResponse maptoNotificationResponse(Notification notification){
        return NotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .userId(notification.getUserId())
                .message(notification.getMessage())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
