package com.example.backendresellkh.dto.request;

import lombok.Data;

@Data
public class ProductHistoryRequest {
    private Long productId;
    private Long userId;
    private String previousStatus;
    private String nextStatus;
}