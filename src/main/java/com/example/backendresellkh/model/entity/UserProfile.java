package com.example.backendresellkh.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    private Long profileId;
    private Long userId;
    private String profileImageUrl;
    private String coverImageUrl;
    private String userName;
    private String firstName;
    private String lastName;
    private String slogan;
    private String location;
    private String telegramUrl;
    private String phoneNumber;
    private String birthDay;
    private String gender;
}
