package com.example.backendresellkh.repository;

import com.example.backendresellkh.model.entity.Otp;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface OtpRepository {
    @Select("SELECT * FROM otps WHERE email = #{email} AND otp_code = #{otpCode} AND is_used = false")
    Optional<Otp> findByEmailAndOtpCode(@Param("email") String email, @Param("otpCode") String otpCode);

    @Update("UPDATE otps SET is_used = true WHERE otp_id = (SELECT otp_id FROM otps WHERE email = #{email} ORDER BY created_at DESC LIMIT 1)")
    void markOtpAsUsedByEmail(@Param("email") String email);

    @Insert("INSERT INTO otps(email, otp_code, created_at, expires_at, is_used)" +
                "VALUES (#{email},#{otpCode}, #{createdAt}, #{expiresAt}, #{isUsed})")
    @Options(useGeneratedKeys = true, keyProperty = "otpId")
    void save(Otp otp);
}
