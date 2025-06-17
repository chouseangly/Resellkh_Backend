package com.example.backendresellkh.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfo {
    private Long contactId;
    private Long userId;
    private Long productId;

    private String profileImageUrl;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String telegramLink;
}
