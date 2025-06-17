package com.example.backendresellkh.controller;

import com.example.backendresellkh.dto.request.ProductRequest;
import com.example.backendresellkh.dto.response.ProductResponse;
import com.example.backendresellkh.model.entity.Product;
import com.example.backendresellkh.model.entity.User;
import com.example.backendresellkh.model.enums.Category;
import com.example.backendresellkh.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest, @AuthenticationPrincipal User currentUser) {
        try {
            Long creatorId = currentUser.getUserId();
            Category category = Category.fromId(productRequest.getCategoryId());
            Product product = Product.builder()
                    .productName(productRequest.getProductName())
                    .userId(creatorId)
                    .categoryId(category.getId())
                    .productCondition(productRequest.getProductCondition())
                    .productStatus(productRequest.getProductStatus())
                    .productDescription(productRequest.getProductDescription())
                    .telegramLink(productRequest.getTelegramLink())
                    .productPrice(productRequest.getProductPrice())
                    .discountPercent(productRequest.getDiscountPercent())
                    .createdAt(LocalDateTime.now())
                    .build();
            ProductResponse productResponse = productService.createProduct(product);
            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        try {
            ProductResponse productResponse = productService.getProductById(productId);
            return ResponseEntity.ok(productResponse);
        } catch (Exception e) {
            log.error("Error getting product by ID {}: {}", productId, e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        try {
            List<ProductResponse> productResponseList = productService.getAllProducts();
            return ResponseEntity.ok(productResponseList);
        } catch (Exception e) {
            log.error("Error getting all products: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/create-with-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> createProductWithImages(
            @RequestParam("productName") String productName,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("productCondition") String productCondition,
            @RequestParam("productStatus") String productStatus,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("telegramLink") String telegramLink,
            @RequestParam("productPrice") Double productPrice,
            @RequestParam("discountPercent") Double discountPercent,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            ProductRequest productRequest,
            @AuthenticationPrincipal User currentUser) {

        try {
            Long creatorId = currentUser.getUserId();
            log.info("Received request to create product with images. Product name: {}", productName);

            // Validate required fields
            if (productName == null || productName.trim().isEmpty()) {
                log.error("Product name is required");
                return ResponseEntity.badRequest().build();
            }
            if (creatorId == null) {
                log.error("User ID is required");
                return ResponseEntity.badRequest().build();
            }
            if (categoryId == null) {
                log.error("Category ID is required");
                return ResponseEntity.badRequest().build();
            }
            if (productPrice == null || productPrice <= 0) {
                log.error("Valid product price is required");
                return ResponseEntity.badRequest().build();
            }

            // Create ProductRequest object from individual parameters
            // 1. Create the product first
            Category category = Category.fromId(productRequest.getCategoryId());
            Product product = Product.builder()
                    .productName(productRequest.getProductName())
                    .userId(creatorId)
                    .categoryId(category.getId())
                    .productCondition(productRequest.getProductCondition())
                    .productStatus(productRequest.getProductStatus())
                    .productDescription(productRequest.getProductDescription())
                    .telegramLink(productRequest.getTelegramLink())
                    .productPrice(productRequest.getProductPrice())
                    .discountPercent(productRequest.getDiscountPercent())
                    .latitude(productRequest.getLatitude())
                    .longitude(productRequest.getLongitude())
                    .createdAt(LocalDateTime.now())
                    .build();

            log.info("Product request created: {}", productRequest);


            ProductResponse productResponse = productService.createProductWithImages(product, images);

            log.info("Product created successfully with ID: {}", productResponse.getProductId());
            return ResponseEntity.ok(productResponse);

        } catch (IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Unexpected error creating product with images: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<Product>> findNearbyProducts(
            @RequestParam double lat,
            @RequestParam double lon) {
        List<Product> nearbyProducts = productService.findNearbyProducts(lat, lon);
        return ResponseEntity.ok(nearbyProducts);
    }
}