package com.example.backendresellkh.service.impl;

import com.example.backendresellkh.dto.request.AuthRequest;
import com.example.backendresellkh.model.entity.User;
import com.example.backendresellkh.model.enums.Role;
import com.example.backendresellkh.repository.AuthRepository;
import com.example.backendresellkh.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(AuthRequest authRequest) {
        if (authRepository.findByEmail(authRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .firstName(authRequest.getFirstName())
                .lastName(authRequest.getLastName())
                .email(authRequest.getEmail())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .createdAt(LocalDateTime.now())
                .role(Role.USER)
                .build();

        authRepository.insertUser(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return authRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public  void enableUserByEmail(String email){
        authRepository.enableUserByEmail(email);
    }

    @Override
    public User resetPassword(@Param("email") String email, @Param("password") String password) {
        // Encode the new password before updating
        String encodedPassword = passwordEncoder.encode(password);
        authRepository.updatePasswordByEmail(email, encodedPassword);
        return findUserByEmail(email);
    }

    @Override
    public boolean userExists(String email) {
        return authRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = authRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return user;
    }
}