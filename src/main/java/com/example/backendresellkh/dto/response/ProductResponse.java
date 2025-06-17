package com.example.backendresellkh.dto.response;

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
public class ProductResponse {
    private Long productId;
    private String productName;
    private int categoryId;
    private String productCondition;
    private String productStatus;
    private String productDescription;
    private String telegramLink;
    private double productPrice;
    private double discountPercent;

    private Double latitude;
    private Double longitude;

    private LocalDateTime createdAt;
    private List<ProductImageResponse> images;
}
