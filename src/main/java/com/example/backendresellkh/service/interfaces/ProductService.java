package com.example.backendresellkh.service.interfaces;

import com.example.backendresellkh.dto.request.ProductRequest;
import com.example.backendresellkh.dto.response.ProductResponse;
import com.example.backendresellkh.model.entity.Product;
import com.example.backendresellkh.model.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(Product product);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
    ProductResponse createProductWithImages(Product product, List<MultipartFile> images);
    List<Product> findNearbyProducts(double latitude, double longitude);
}
