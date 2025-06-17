package com.example.backendresellkh.controller;

import com.example.backendresellkh.dto.request.ProductImageRequest;
import com.example.backendresellkh.dto.response.ProductImageResponse;
import com.example.backendresellkh.service.interfaces.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/productImages")
@RequiredArgsConstructor
public class ProductImageController {
    private final ProductImageService productImageService;

    @PostMapping
    public ResponseEntity<ProductImageResponse> createProductImage(@RequestBody ProductImageRequest productImageRequest) {
        ProductImageResponse response = productImageService.createProductImage(productImageRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // New endpoint for uploading multiple images with files
    @PostMapping(value = "/upload/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ProductImageResponse>> uploadProductImages(
            @PathVariable Long productId,
            @RequestParam("images") List<MultipartFile> images) {

        List<ProductImageResponse> responses = productImageService.createProductImagesFromFiles(productId, images);
        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductImageResponse>> getAllProductImages() {
        List<ProductImageResponse> responses = productImageService.getAllProductImages();
        return ResponseEntity.ok(responses);
    }


}