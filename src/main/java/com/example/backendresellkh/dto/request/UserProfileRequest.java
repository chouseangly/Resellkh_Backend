package com.example.backendresellkh.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserProfileRequest {
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
