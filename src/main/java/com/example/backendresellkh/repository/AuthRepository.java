package com.example.backendresellkh.repository;

import com.example.backendresellkh.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface AuthRepository {
    @Select("SELECT * FROM users WHERE email = #{email}")
    Optional<User> findByEmail(@Param("email") String email);

    @Insert("INSERT INTO users(first_name, last_name, email, password, created_at, role) " +
            "VALUES (#{firstName}, #{lastName}, #{email}, #{password}, #{createdAt}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void insertUser(User user);

    @Update("UPDATE users SET enabled = true WHERE email = #{email}")
    void enableUserByEmail(String email);

    @Update("UPDATE users SET password = #{password} WHERE email = #{email}")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

    @Select("SELECT COUNT(*) > 0 FROM users WHERE email = #{email}")
    boolean existsByEmail(@Param("email") String email);
}
