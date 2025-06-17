package com.example.backendresellkh.dto.request;

import lombok.Data;

@Data
public class RatingRequest {
    private Long userId;
    private Long userRatedId;
    private double ratingValue;
    private String ratingDescription;
}
