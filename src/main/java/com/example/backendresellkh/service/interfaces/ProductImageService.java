package com.example.backendresellkh.service.interfaces;

import com.example.backendresellkh.dto.request.ProductImageRequest;
import com.example.backendresellkh.dto.response.ProductImageResponse;
import com.example.backendresellkh.model.entity.ProductImage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    ProductImageResponse createProductImage(ProductImageRequest productImageRequest);
    ProductImageResponse getImageByProductId(Long productId);
    List<ProductImage> getProductImageByProductId(Long productId);
    List<ProductImageResponse> getAllProductImages();
    List<ProductImageResponse> createProductImagesFromFiles(Long productId, List<MultipartFile> images);
}
