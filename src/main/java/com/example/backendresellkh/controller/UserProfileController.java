package com.example.backendresellkh.controller;

import com.example.backendresellkh.dto.request.UserProfileRequest;
import com.example.backendresellkh.dto.response.ProductResponse;
import com.example.backendresellkh.dto.response.UserProfileResponse;
import com.example.backendresellkh.model.entity.Product;
import com.example.backendresellkh.model.entity.User;
import com.example.backendresellkh.model.entity.UserProfile;
import com.example.backendresellkh.model.enums.Category;
import com.example.backendresellkh.service.interfaces.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/userprofile")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping("/createuserprofile")
    public ResponseEntity<UserProfileResponse> createUserProfile(@RequestBody UserProfileRequest userProfileRequest, @AuthenticationPrincipal User currentUser) {
        Long userId = currentUser.getUserId();
        UserProfile userProfile = UserProfile.builder()
                .userId(userId)
                .profileImageUrl(userProfileRequest.getProfileImageUrl())
                .coverImageUrl(userProfileRequest.getCoverImageUrl())
                .userName(userProfileRequest.getUserName())
                .firstName(userProfileRequest.getFirstName())
                .lastName(userProfileRequest.getLastName())
                .slogan(userProfileRequest.getSlogan())
                .location(userProfileRequest.getLocation())
                .phoneNumber(userProfileRequest.getPhoneNumber())
                .telegramUrl(userProfileRequest.getTelegramUrl())
                .birthDay(userProfileRequest.getBirthDay())
                .gender(userProfileRequest.getGender())
                .build();
        userProfileService.createUserProfile(userProfile);
        return new ResponseEntity<>(UserProfileResponse.builder().userId(userId).build(), HttpStatus.CREATED);
    }

    @GetMapping("/getprofilebyuserid")
    public ResponseEntity<UserProfileResponse> getUserProfile(@AuthenticationPrincipal User currentUser) {
        Long userId = currentUser.getUserId();
        userProfileService.getUserProfileByUserId(userId);
        return new ResponseEntity<>(UserProfileResponse.builder().userId(userId).build(), HttpStatus.OK);
    }
}
