package com.example.backendresellkh.service.interfaces;

import com.example.backendresellkh.dto.request.AuthRequest;
import com.example.backendresellkh.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface AuthService extends UserDetailsService {
    User findUserByEmail(String email);
    void registerUser(AuthRequest authRequest);
    User resetPassword(@Param("email") String email, @Param("password") String password);
    void enableUserByEmail(String email);
    boolean userExists(String email);
    UserDetails loadUserByUsername(String email);
}