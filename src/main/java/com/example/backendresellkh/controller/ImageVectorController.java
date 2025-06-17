package com.example.backendresellkh.controller;

import com.example.backendresellkh.dto.request.ImageVectorRequest;
import com.example.backendresellkh.service.interfaces.ImageVectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageVectorController {

    private final ImageVectorService imageVectorService;

    @PostMapping("/vector")
    public List<Double> getVectorFromUrl(@RequestParam String url) {
        return imageVectorService.extractVectorFromUrl(url);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ImageVectorRequest request) {
        imageVectorService.createImageWithVector(request);
        return ResponseEntity.ok("Image and vector saved successfully.");
    }
}
