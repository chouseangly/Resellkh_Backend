package com.example.backendresellkh.external.client;

import com.example.backendresellkh.dto.response.ImageVectorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class PythonVectorClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String PYTHON_API_URL = "http://localhost:8000/extract-vector";

    public float[] extractVector(String imageUrl) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("url", imageUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<ImageVectorResponse> response = restTemplate.postForEntity(PYTHON_API_URL, requestEntity, ImageVectorResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return toPrimitive(response.getBody().getVectorData());
        } else {
            throw new RuntimeException("Failed to extract image vector");
        }
    }

    private float[] toPrimitive(java.util.List<Float> list) {
        if (list == null) {
            throw new IllegalArgumentException("Vector data is null");
            // OR: return new float[0]; if you want to allow empty result
        }
        float[] result = new float[list.size()];
        for (int i = 0; i < list.size(); i++) result[i] = list.get(i);
        return result;
    }
}
