package com.example.backendresellkh.dto.request;

import lombok.Data;

@Data
public class ImageVectorRequest {
    private Long imageId;
    private String imageUrl;
    private String vectorData;
}
