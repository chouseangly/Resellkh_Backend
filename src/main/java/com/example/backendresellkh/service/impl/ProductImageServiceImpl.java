package com.example.backendresellkh.service.impl;


import com.example.backendresellkh.dto.request.ProductImageRequest;
import com.example.backendresellkh.dto.response.ProductImageResponse;
import com.example.backendresellkh.model.entity.ProductImage;
import com.example.backendresellkh.repository.ProductImageRepository;
import com.example.backendresellkh.repository.ProductRepository;
import com.example.backendresellkh.service.interfaces.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final PinataService pinataService;

    @Override
    public ProductImageResponse createProductImage(ProductImageRequest productImageRequest) {
        productRepository.getProductById(productImageRequest.getProductId());
        ProductImage productImage = ProductImage.builder()
                .productId(productImageRequest.getProductId())
                .imageUrl(productImageRequest.getImageUrl())
                .build();
        productImageRepository.createProductImage(productImage);
        return mapToProductImageResponse(productImage);
    }

    @Override
    public ProductImageResponse getImageByProductId(Long productId) {
        ProductImage productImage = productImageRepository.getImageByProductId(productId)
                .orElseThrow(() -> new RuntimeException("ProductId not found"));
        return mapToProductImageResponse(productImage);
    }

    @Override
    public List<ProductImageResponse> getAllProductImages() {
        return productImageRepository.getAll()
                .stream()
                .map(this::mapToProductImageResponse)
                .toList();
    }

    private ProductImageResponse mapToProductImageResponse(ProductImage productImage) {
        return ProductImageResponse.builder()
                .imageId(productImage.getImageId())
                .productId(productImage.getProductId())
                .imageUrl(productImage.getImageUrl())
                .build();
    }

    @Override
    public List<ProductImageResponse> createProductImagesFromFiles(Long productId, List<MultipartFile> images) {
        productRepository.getProductById(productId);

        if (images == null || images.isEmpty()) {
            return new ArrayList<>();
        }

        if (images.size() > 5) {
            throw new IllegalArgumentException("Maximum 5 images allowed per product");
        }

        List<ProductImage> productImages = new ArrayList<>();

        try {
            for (int i = 0; i < images.size(); i++) {
                MultipartFile image = images.get(i);

                if (!pinataService.isValidImage(image)) {
                    throw new IllegalArgumentException("Invalid image format: " + image.getOriginalFilename());
                }

                String imageUrl = pinataService.uploadImageToPinata(image);

                ProductImage productImage = ProductImage.builder()
                        .productId(productId)
                        .imageUrl(imageUrl)
                        .build();

                productImages.add(productImage);
            }

            productImageRepository.createProductImages(productImages);

            return productImages.stream()
                    .map(this::mapToProductImageResponse)
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload images: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ProductImage> getProductImageByProductId(Long productId){
        return productImageRepository.getProductImageByProductId(productId);
    }

}
