package com.example.backendresellkh.repository;

import com.example.backendresellkh.model.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductRepository {
    @Insert("INSERT INTO products(product_name, user_id, category_id, product_condition, product_status, product_description, telegram_link, product_price, discount_percent, latitude, longitude, created_at)"+
                "VALUES (#{productName}, #{userId}, #{categoryId}, #{productCondition}, #{productStatus}, #{productDescription}, #{telegramLink}, #{productPrice}, #{discountPercent}, #{latitude}, #{longitude}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "productId")
    void createProduct(Product product);

    @Select("SELECT * FROM products WHERE product_id = #{productId}")
    Product getProductById(@Param("productId") Long productId);

    @Select("SELECT * FROM products")
    List<Product> getAllProducts();

    @Select("""
        SELECT * FROM products
        WHERE (
            6371 * acos(
                cos(radians(#{lat})) * cos(radians(latitude)) *
                cos(radians(longitude) - radians(#{lon})) +
                sin(radians(#{lat})) * sin(radians(latitude))
            )
        ) < 5
        """)
    List<Product> findNearbyProducts(@Param("lat") double lat,
                                     @Param("lon") double lon);

}
