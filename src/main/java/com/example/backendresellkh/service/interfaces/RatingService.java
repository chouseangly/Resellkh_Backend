package com.example.backendresellkh.service.interfaces;

import com.example.backendresellkh.dto.request.RatingRequest;
import com.example.backendresellkh.dto.response.RatingResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    RatingResponse getRatingByUserId(Long userId);
    RatingResponse getRatingByUserRatedId(Long userId);
    List<RatingResponse> getAllRating();
    RatingResponse createRating(RatingRequest ratingRequest);
}
