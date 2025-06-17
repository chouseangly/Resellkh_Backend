package com.example.backendresellkh.service.interfaces;

import com.example.backendresellkh.dto.request.ImageVectorRequest;

import java.util.List;

public interface ImageVectorService {
    List<Double> extractVectorFromUrl(String imageUrl);
    void createImageWithVector(ImageVectorRequest request);
    void saveImageVector(Long imageId, List<Double> vector);
    List<String> findSimilarImages(List<Double> queryVector, int topK);
}
