package com.example.backendresellkh.dto.request;

import lombok.Data;

@Data
public class FavoriteRequest {
    private Long userId;
    private Long productId;
}
