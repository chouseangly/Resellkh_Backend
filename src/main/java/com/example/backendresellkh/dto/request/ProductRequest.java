package com.example.backendresellkh.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductRequest {
    private String productName;
    private Integer categoryId;
    private String productCondition;
    private String productStatus;
    private String productDescription;
    private String telegramLink;
    private Double productPrice;
    private Double discountPercent;

    private Double latitude;
    private Double longitude;
}
