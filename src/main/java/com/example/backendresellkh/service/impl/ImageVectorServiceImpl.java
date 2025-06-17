package com.example.backendresellkh.service.impl;

import com.example.backendresellkh.dto.request.ImageVectorRequest;
import com.example.backendresellkh.external.client.ImageVectorClient;
import com.example.backendresellkh.external.client.PythonVectorClient;
import com.example.backendresellkh.model.entity.ImageVector;
import com.example.backendresellkh.repository.ImageVectorRepository;
import com.example.backendresellkh.service.interfaces.ImageVectorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageVectorServiceImpl implements ImageVectorService {

    private final ImageVectorClient imageVectorClient;
    private final PythonVectorClient pythonClient;
    private final ImageVectorRepository imageVectorRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<Double> extractVectorFromUrl(String imageUrl) {
        return imageVectorClient.getImageVector(imageUrl);
    }

    @Override
    public void createImageWithVector(ImageVectorRequest request) {
        String jsonVectorString;
        float[] vectorFloatArray = pythonClient.extractVector(request.getImageUrl());

        List<Double> vectorDataList = IntStream.range(0, vectorFloatArray.length)
                .mapToDouble(i -> vectorFloatArray[i])
                .boxed()
                .collect(Collectors.toList());

        try {
            jsonVectorString = objectMapper.writeValueAsString(vectorDataList);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to convert image vector to JSON string", e);
        }
        try {
            imageVectorRepository.updateVectorByImageId(request.getImageId(), jsonVectorString);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save image vector to database", e);
        }



        ImageVector image = new ImageVector();
        image.setImageUrl(request.getImageUrl());
        image.setVectorData(image.getVectorData());

    }

    @Override
    public void saveImageVector(Long imageId, List<Double> vectorData) {
        String jsonVectorString;
        try {
            jsonVectorString = objectMapper.writeValueAsString(vectorData);

        } catch (JsonProcessingException e) {
            System.err.println("Error converting vector to JSON for imageId " + imageId + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to convert image vector to JSON string", e);
        }

        try {
            imageVectorRepository.updateVectorByImageId(imageId, jsonVectorString);

        } catch (Exception e) {
            System.err.println("Error saving image vector for imageId " + imageId + " to database: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save image vector to database", e);
        }
    }

    @Override
    public List<String> findSimilarImages(List<Double> queryVector, int topK) {
        List<ImageVectorRequest> all = imageVectorRepository.getAllVectors();

        List<ImageSimilarityResult> scored = new ArrayList<>();

        for (ImageVectorRequest dto : all) {
            try {
                List<Double> dbVector = objectMapper.readValue(dto.getVectorData(), new TypeReference<>() {});
                double score = cosineSimilarity(queryVector, dbVector);
                scored.add(new ImageSimilarityResult(dto.getImageUrl(), score));
            } catch (Exception e) {
                System.err.println("Failed to parse vector for imageId " + dto.getImageId());
            }
        }

        return scored.stream()
                .sorted((a, b) -> Double.compare(b.score, a.score)) // descending
                .limit(topK)
                .map(result -> result.imageUrl)
                .collect(Collectors.toList());
    }

    private double cosineSimilarity(List<Double> a, List<Double> b) {
        if (a.size() != b.size()) return -1;

        double dot = 0.0, normA = 0.0, normB = 0.0;
        for (int i = 0; i < a.size(); i++) {
            dot += a.get(i) * b.get(i);
            normA += a.get(i) * a.get(i);
            normB += b.get(i) * b.get(i);
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    record ImageSimilarityResult(String imageUrl, double score) {}

}
