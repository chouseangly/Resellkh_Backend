package com.example.backendresellkh.external.client;

import lombok.Data;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Component
public class ImageVectorClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String pythonApiUrl = "http://127.0.0.1:8000/extract-vector"; // Your local Python FastAPI URL

    public List<Double> getImageVector(String imageUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request = Map.of("url", imageUrl);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<VectorResponse> response = restTemplate.exchange(
                pythonApiUrl,
                HttpMethod.POST,
                entity,
                VectorResponse.class
        );

        return response.getBody() != null ? response.getBody().getVector() : null;
    }

    @Data
    private static class VectorResponse {
        private List<Double> vector;
    }

    public List<Double> getImageVectorFromFile(MultipartFile file) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            // Wrap file content
            ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename(); // 👈 MUST set filename
                }
            };

            body.add("image", resource); // 👈 MUST match FastAPI param name

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<VectorResponse> response = restTemplate.exchange(
                    "http://127.0.0.1:8000/extract-vector-from-upload",
                    HttpMethod.POST,
                    requestEntity,
                    VectorResponse.class
            );

            return response.getBody() != null ? response.getBody().getVector() : null;

        } catch (Exception e) {
            throw new RuntimeException("Failed to extract vector from uploaded image", e);
        }
    }

}
