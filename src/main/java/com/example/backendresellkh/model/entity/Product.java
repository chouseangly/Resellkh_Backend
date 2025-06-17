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
public class Product {
    private Long productId;
    private String productName;
    private Long userId;
    private int categoryId;
    private String productCondition;
    private String productStatus;
    private String productDescription;
    private String telegramLink;
    private double productPrice;
    private double discountPercent;

    private double latitude;
    private double longitude;

    private LocalDateTime createdAt;
    private ListStatus SOLD_STATUS;
    private List<ProductImageResponse> images;
}
