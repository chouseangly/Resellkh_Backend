package com.example.backendresellkh.repository;

import com.example.backendresellkh.model.entity.ProductHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductHistoryRepository {

    @Insert("INSERT INTO product_history(product_name, user_id, product_id, product_cover_url, product_description, product_price, previous_status, next_status, created_at) " +
            "VALUES (#{productName}, #{userId}, #{productId}, #{productCoverUrl}, #{productDescription}, #{productPrice}, #{previousStatus}, #{nextStatus}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "productHistoryId")
    void createProductHistory(ProductHistory productHistory);

    @Select("SELECT * FROM product_history WHERE product_history_id = #{productHistoryId}")
    ProductHistory getProductHistoryById(@Param("productHistoryId") Long productHistoryId);


    @Select("SELECT * FROM product_history WHERE product_id = #{productId}")
    List<ProductHistory> getProductHistoriesByProductId(@Param("productId") Long productId);


    @Select("SELECT * FROM product_history")
    List<ProductHistory> getAllProductHistories();
}