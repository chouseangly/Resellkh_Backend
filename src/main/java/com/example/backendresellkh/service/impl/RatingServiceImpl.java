package com.example.backendresellkh.service.impl;

import com.example.backendresellkh.dto.request.RatingRequest;
import com.example.backendresellkh.dto.response.RatingResponse;
import com.example.backendresellkh.model.entity.Rating;
import com.example.backendresellkh.repository.RatingRepository;
import com.example.backendresellkh.service.interfaces.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    @Override
    public RatingResponse getRatingByUserId(Long userId) {
        Rating rating = ratingRepository.getRatingByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId.toString()));
        return maptoRatingResponse(rating);
    }

    @Override
    public RatingResponse getRatingByUserRatedId(Long userId) {
        Rating rating = ratingRepository.getRatingByUserRatedId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId.toString()));
        return maptoRatingResponse(rating);
    }

    @Override
    public List<RatingResponse> getAllRating() {
        return ratingRepository.getAllRatings()
                .stream()
                .map(this::maptoRatingResponse)
                .toList();
    }

    @Override
    public RatingResponse createRating(RatingRequest ratingRequest) {
        Rating rating = Rating.builder()
                .userId(ratingRequest.getUserId())
                .userRatedId(ratingRequest.getUserRatedId())
                .ratingValue(ratingRequest.getRatingValue())
                .ratingDescription(ratingRequest.getRatingDescription())
                .createdAt(LocalDateTime.now())
                .build();
        ratingRepository.createRating(rating);
        return maptoRatingResponse(rating);
    }

    private RatingResponse maptoRatingResponse(Rating rating) {
        return RatingResponse.builder()
                .ratingId(rating.getRatingId())
                .userId(rating.getUserId())
                .userRatedId(rating.getUserRatedId())
                .ratingValue(rating.getRatingValue())
                .ratingDescription(rating.getRatingDescription())
                .createdAt(rating.getCreatedAt())
                .build();
    }
}
