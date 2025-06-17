package com.example.backendresellkh.repository;

import com.example.backendresellkh.model.entity.Rating;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RatingRepository {
    @Insert("INSERT INTO rating(rating_id, user_id, user_rated_id, rating_value, rating_description, created_at)" +
            "VALUES (#{ratingId}, #{userId}, #{userRatedId}, #{ratingValue}, #{ratingDescription}, #{createdAt})")
    void createRating(Rating rating);

    @Select("SELECT * FROM rating")
    List<Rating> getAllRatings();

    @Select("SELECT * FROM rating WHERE user_id = #{userId}")
    Optional<Rating> getRatingByUserId(@Param("userId") Long userId);

    @Select("SELECT * FROM rating WHERE user_rated_id = #{userId}")
    Optional<Rating> getRatingByUserRatedId(@Param("userId") Long userId);
}
