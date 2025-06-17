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
public class ProductHistoryResponse {
    private Long productHistoryId;
    private Long productId;
    private Long userId;
    private String productName;
    private String productCoverUrl;
    private String productDescription;
    private double productPrice;
    private String previousStatus;
    private String nextStatus;
    private LocalDateTime createdAt;
}