package com.example.backendresellkh.service.impl;

import com.example.backendresellkh.dto.request.ProductHistoryRequest;
import com.example.backendresellkh.dto.response.ProductHistoryResponse;
import com.example.backendresellkh.model.entity.ProductHistory;
import com.example.backendresellkh.repository.ProductHistoryRepository;
import com.example.backendresellkh.service.interfaces.ProductHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductHistoryServiceImpl implements ProductHistoryService {
    private final ProductHistoryRepository productHistoryRepository;

    @Override
    public ProductHistoryResponse createProductHistory(ProductHistoryRequest productHistoryRequest) {
        ProductHistory productHistory = ProductHistory.builder()
                .productId(productHistoryRequest.getProductId())
                .userId(productHistoryRequest.getUserId())
                .previousStatus(productHistoryRequest.getPreviousStatus())
                .nextStatus(productHistoryRequest.getNextStatus())
                .createdAt(LocalDateTime.now())
                .build();
        productHistoryRepository.createProductHistory(productHistory);
        return mapToProductHistoryResponse(productHistory);
    }

    @Override
    public ProductHistoryResponse getProductHistoryById(Long id) {
        ProductHistory productHistory = productHistoryRepository.getProductHistoryById(id);
        return mapToProductHistoryResponse(productHistory);
    }

    @Override
    public List<ProductHistoryResponse> getAllProductHistories() {
        return productHistoryRepository.getAllProductHistories()
                .stream()
                .map(this::mapToProductHistoryResponse)
                .toList();
    }

    @Override
    public List<ProductHistoryResponse> getProductHistoriesByProductId(Long productId) {
        return productHistoryRepository.getProductHistoriesByProductId(productId)
                .stream()
                .map(this::mapToProductHistoryResponse)
                .toList();
    }

    private ProductHistoryResponse mapToProductHistoryResponse(ProductHistory productHistory) {
        return ProductHistoryResponse.builder()
                .productHistoryId(productHistory.getProductHistoryId())
                .productId(productHistory.getProductId())
                .userId(productHistory.getUserId())
                .previousStatus(productHistory.getPreviousStatus())
                .nextStatus(productHistory.getNextStatus())
                .createdAt(productHistory.getCreatedAt())
                .build();
    }
}