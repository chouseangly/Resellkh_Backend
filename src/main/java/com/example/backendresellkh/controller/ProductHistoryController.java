package com.example.backendresellkh.controller;

import com.example.backendresellkh.dto.request.ProductHistoryRequest;
import com.example.backendresellkh.dto.response.ProductHistoryResponse;
import com.example.backendresellkh.service.interfaces.ProductHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-history")
@RequiredArgsConstructor
@Slf4j
public class ProductHistoryController {
    private final ProductHistoryService productHistoryService;

    @PostMapping
    public ResponseEntity<ProductHistoryResponse> createProductHistory(@RequestBody ProductHistoryRequest productHistoryRequest) {
        try {
            ProductHistoryResponse productHistoryResponse = productHistoryService.createProductHistory(productHistoryRequest);
            return new ResponseEntity<>(productHistoryResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating product history: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{historyId}")
    public ResponseEntity<ProductHistoryResponse> getProductHistoryById(@PathVariable Long historyId) {
        try {
            ProductHistoryResponse productHistoryResponse = productHistoryService.getProductHistoryById(historyId);
            return ResponseEntity.ok(productHistoryResponse);
        } catch (Exception e) {
            log.error("Error getting product history by ID {}: {}", historyId, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductHistoryResponse>> getAllProductHistories() {
        try {
            List<ProductHistoryResponse> productHistoryResponseList = productHistoryService.getAllProductHistories();
            return ResponseEntity.ok(productHistoryResponseList);
        } catch (Exception e) {
            log.error("Error getting all product histories: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<ProductHistoryResponse>> getProductHistoriesByProductId(@PathVariable Long productId) {
        try {
            List<ProductHistoryResponse> productHistoryResponseList = productHistoryService.getProductHistoriesByProductId(productId);
            return ResponseEntity.ok(productHistoryResponseList);
        } catch (Exception e) {
            log.error("Error getting product histories for product ID {}: {}", productId, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}