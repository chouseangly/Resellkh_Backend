package com.example.backendresellkh.service.interfaces;

import com.example.backendresellkh.dto.response.UserProfileResponse;
import com.example.backendresellkh.model.entity.UserProfile;

public interface UserProfileService {
    void createUserProfile(UserProfile userProfile);
    UserProfileResponse getUserProfileByUserId(Long userId);
}
