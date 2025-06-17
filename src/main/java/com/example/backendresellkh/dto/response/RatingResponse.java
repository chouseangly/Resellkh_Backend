package com.example.backendresellkh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponse {
    private Long ratingId;
    private Long userId;
    private Long userRatedId;
    private double ratingValue;
    private String ratingDescription;
    private LocalDateTime createdAt;
}
