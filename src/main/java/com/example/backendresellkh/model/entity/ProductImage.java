package com.example.backendresellkh.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {
    private Long imageId;
    private Long productId;
    private String imageUrl;
}
