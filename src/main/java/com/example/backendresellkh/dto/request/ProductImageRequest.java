package com.example.backendresellkh.dto.request;

import lombok.Data;

@Data
public class ProductImageRequest {
    private Long productId;
    private String imageUrl;
}
