package com.example.backendresellkh.repository;

import com.example.backendresellkh.model.entity.ContactInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContactInfoRepository {
    @Select("SELECT up.profile_image_url, u.first_name, u.last_name, up.phone_number, p.telegram_link " +
            "FROM users u LEFT JOIN user_profiles up ON u.user_id = up.user_id " +
            "JOIN products p ON p.user_id = u.user_id " +
            "WHERE p.product_id = #{productId}")
    List<ContactInfo> getContactInfoByProductId(@Param("productId") Long productId);
}

