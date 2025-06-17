package com.example.backendresellkh.model.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    private Long ratingId;
    private Long userId;
    private Long userRatedId;
    private double ratingValue;
    private String ratingDescription;
    private LocalDateTime createdAt;
}
