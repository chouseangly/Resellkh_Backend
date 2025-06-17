package com.example.backendresellkh.repository;

import com.example.backendresellkh.model.entity.UserProfile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserProfileRepository {
    @Insert("INSERT INTO user_profile(user_id, profile_image_url, cover_image_url, user_name, first_name, last_name,slogan, location, telegram_url, phone_number, birth_day, gender)" +
            "VALUES (#{userId}, #{profileImageUrl}, #{coverImageUrl}, #{userName}, #{fistName}, #{lastName}, #{slogan}, #{location}, #{telegramUrl},#{phoneNumber}, #{birthDay}, #{gender})")
    void insertUserProfile(UserProfile userProfile);

    @Select("SELECT * FROM user_profile WHERE user_id = #{userId}")
    UserProfile getUserProfileByUserId(@Param("userId") Long userId);


}
