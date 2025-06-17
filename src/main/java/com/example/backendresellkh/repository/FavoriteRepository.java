package com.example.backendresellkh.repository;

import com.example.backendresellkh.model.entity.Favorite;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FavoriteRepository {
    @Insert("INSERT INTO favorites(favorite_id, user_id, product_id" + "VALUES (#{favoriteId}, #{userId}, #{productId})")
    void createFavorite(Favorite favorite);

    @Select("SELECT * FROM favorites")
    List<Favorite> getAllFavorites();
}
