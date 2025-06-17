package com.example.backendresellkh.repository;

import com.example.backendresellkh.model.entity.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationRepository {

    @Select("SELECT * FROM notifications")
    List<Notification> findAll();

    @Insert("INSERT INTO notifications(user_id, message, is_read, created_at)" +
                "VALUES(#{userId}, #{message}, #{isRead}, #{createdAt})")
    void createNotification(Notification notification);
}
