package com.example.backendresellkh.service.interfaces;

import com.example.backendresellkh.dto.request.ProductHistoryRequest;
import com.example.backendresellkh.dto.response.ProductHistoryResponse;

import java.util.List;

public interface ProductHistoryService {
    ProductHistoryResponse createProductHistory(ProductHistoryRequest productHistoryRequest);
    ProductHistoryResponse getProductHistoryById(Long id);
    List<ProductHistoryResponse> getAllProductHistories();
    List<ProductHistoryResponse> getProductHistoriesByProductId(Long productId);
}