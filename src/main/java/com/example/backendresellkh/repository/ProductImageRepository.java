package com.example.backendresellkh.repository;

import com.example.backendresellkh.model.entity.ProductImage;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductImageRepository {

    @Select("SELECT * FROM product_images")
    List<ProductImage> getAll();

    @Select("SELECT * FROM product_images WHERE product_id = #{productId}")
    List<ProductImage> getProductImageByProductId(@Param("productId") Long productId);

    // Fixed: This should return a list, not a single image
    @Select("SELECT * FROM product_images WHERE product_id = #{productId}")
    List<ProductImage> getImagesByProductId(@Param("productId") Long productId);

    // Single image insert with generated key for PostgreSQL
    @Insert("INSERT INTO product_images(product_id, image_url) " +
            "VALUES (#{productId}, #{imageUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "imageId")
    void createProductImage(ProductImage productImage);

    // Batch insert for multiple images for PostgreSQL
    @Insert({
            "<script>",
            "INSERT INTO product_images(product_id, image_url) VALUES ",
            "<foreach collection='images' item='image' separator=','>",
            "(#{image.productId}, #{image.imageUrl})",
            "</foreach>",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "imageId")
    void createProductImages(@Param("images") List<ProductImage> images);

    @Select("SELECT * FROM product_images WHERE product_id = #{productId} LIMIT 1")
    Optional<ProductImage> getImageByProductId(@Param("productId") Long productId);

}