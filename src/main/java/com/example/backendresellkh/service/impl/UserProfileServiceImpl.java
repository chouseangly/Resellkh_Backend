package com.example.backendresellkh.service.impl;

import com.example.backendresellkh.dto.response.UserProfileResponse;
import com.example.backendresellkh.model.entity.UserProfile;
import com.example.backendresellkh.repository.UserProfileRepository;
import com.example.backendresellkh.service.interfaces.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;

    @Override
    public void createUserProfile(UserProfile userProfile){
        userProfileRepository.insertUserProfile(userProfile);
    }

    @Override
    public UserProfileResponse getUserProfileByUserId(Long userId){
        UserProfile userProfile = userProfileRepository.getUserProfileByUserId(userId);
        if (userProfile == null) {
            throw new RuntimeException("userProfile not found");
        }
        return new UserProfileResponse(
                userProfile.getProfileId(),
                userProfile.getUserId(),
                userProfile.getProfileImageUrl(),
                userProfile.getCoverImageUrl(),
                userProfile.getUserName(),
                userProfile.getFirstName(),
                userProfile.getLastName(),
                userProfile.getSlogan(),
                userProfile.getLocation(),
                userProfile.getTelegramUrl(),
                userProfile.getPhoneNumber(),
                userProfile.getBirthDay() != null ? userProfile.getBirthDay().toString() : null,
                userProfile.getGender()
        );
    }
}
