package com.example.backendresellkh.service.impl;

import com.example.backendresellkh.dto.request.ImageVectorRequest;
import com.example.backendresellkh.dto.request.ProductRequest;
import com.example.backendresellkh.dto.response.ProductImageResponse;
import com.example.backendresellkh.dto.response.ProductResponse;
import com.example.backendresellkh.external.client.PythonVectorClient;
import com.example.backendresellkh.model.entity.ImageVector;
import com.example.backendresellkh.model.entity.Product;
import com.example.backendresellkh.model.entity.ProductImage;
import com.example.backendresellkh.model.entity.User;
import com.example.backendresellkh.model.enums.Category;
import com.example.backendresellkh.repository.ProductImageRepository;
import com.example.backendresellkh.repository.ProductRepository;
import com.example.backendresellkh.service.interfaces.ImageVectorService;
import com.example.backendresellkh.service.interfaces.ProductImageService;
import com.example.backendresellkh.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    private final PythonVectorClient pythonVectorClient;
    private final ImageVectorService imageVectorService;
    private final ProductImageRepository productImageRepository;

    @Override
    public ProductResponse createProduct(Product product) {
        productRepository.createProduct(product);
        return mapToProductResponse(product);
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.getProductById(productId);
        return mapToProductResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.getAllProducts()
                .stream()
                .map(this::mapToProductResponse)
                .toList();
    }

    @Override
    public ProductResponse createProductWithImages(Product product, List<MultipartFile> images) {
        try {
            ProductResponse productResponse = createProduct(product);
            Long productId = productResponse.getProductId(); // Get the generated ID

            // 2. Upload and save images using the product ID
            if (images != null && !images.isEmpty()) {
                List<ProductImageResponse> imageResponses = productImageService
                        .createProductImagesFromFiles(productId, images);

                // Set images in product response
                productResponse.setImages(imageResponses);
            }

            List<ProductImage> productImages = productImageService.getProductImageByProductId(productId);
            List<ProductImageResponse> imageResponses = productImages.stream()
                    .map(this::mapToProductImageResponse) // You need to implement this mapping method
                    .collect(Collectors.toList());


            for (ProductImageResponse image : imageResponses) {
                try {
                    String urlString = image.getImageUrl();

                    List<Double> vector = imageVectorService.extractVectorFromUrl(image.getImageUrl());
                    imageVectorService.saveImageVector(image.getImageId(), vector);
                } catch (Exception e) {
                    System.err.println("Failed to extract vector for image: " + image.getImageUrl());
                    e.printStackTrace(); // You can log this properly
                }
            }

            return productResponse;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create product with images: " + e.getMessage(), e);
        }
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .categoryId(product.getCategoryId())
                .productCondition(product.getProductCondition())
                .productStatus(product.getProductStatus())
                .productDescription(product.getProductDescription())
                .telegramLink(product.getTelegramLink())
                .productPrice(product.getProductPrice())
                .discountPercent(product.getDiscountPercent())
                .latitude(product.getLatitude())
                .longitude(product.getLongitude())
                .createdAt(product.getCreatedAt())
                .build();
    }

    private ProductImageResponse mapToProductImageResponse(ProductImage productImage) {
        return ProductImageResponse.builder()
                .imageId(productImage.getImageId())
                .productId(productImage.getProductId())
                .imageUrl(productImage.getImageUrl())
                .build();
    }

    @Override
    public List<Product> findNearbyProducts(double latitude, double longitude) {
        return productRepository.findNearbyProducts(latitude, longitude);
    }
}
