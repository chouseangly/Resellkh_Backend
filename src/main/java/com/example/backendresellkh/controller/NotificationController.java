package com.example.backendresellkh.controller;

import com.example.backendresellkh.dto.request.NotificationRequest;
import com.example.backendresellkh.dto.response.NotificationResponse;
import com.example.backendresellkh.service.interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(@RequestBody NotificationRequest notification){
        NotificationResponse notificationResponse = notificationService.createNotification(notification);
        return new ResponseEntity<>(notificationResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAllNotifications(){
        List<NotificationResponse> notifications = notificationService.getAllNotifications();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
}
