package com.example.backendresellkh.repository;

import com.example.backendresellkh.dto.request.ImageVectorRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ImageVectorRepository {
    @Update("UPDATE product_images SET vector_data = #{vectorData}::jsonb WHERE image_id = #{imageId}")
    void updateVectorByImageId(@Param("imageId") Long imageId, @Param("vectorData") String vectorData);

    @Select("SELECT image_id, image_url, vector_data FROM product_images WHERE vector_data IS NOT NULL")
    List<ImageVectorRequest> getAllVectors();

}
