package com.example.backendresellkh.model.entity;

import com.example.backendresellkh.dto.response.ProductImageResponse;
import com.example.backendresellkh.model.enums.ListStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductHistory {
    private Long productHistoryId;
    private String productName;
    private Long userId;
    private Long productId;
    private String productCoverUrl;
    private String productDescription;
    private double productPrice;
    private String previousStatus;
    private String nextStatus;
    private LocalDateTime createdAt;
}
