package com.example.backendresellkh.controller;

import com.example.backendresellkh.dto.request.AuthRequest;
import com.example.backendresellkh.dto.request.GenerateOtpRequest;
import com.example.backendresellkh.dto.request.OtpRequest;
import com.example.backendresellkh.dto.request.ResetPasswordRequest;
import com.example.backendresellkh.dto.response.AuthResponse;
import com.example.backendresellkh.jwt.JwtService;
import com.example.backendresellkh.model.entity.User;
import com.example.backendresellkh.model.enums.Role;
import com.example.backendresellkh.repository.AuthRepository;
import com.example.backendresellkh.service.interfaces.AuthService;
import com.example.backendresellkh.service.interfaces.OtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API endpoints")
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthRepository authRepository;
    private final OtpService otpService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        try {
            authService.registerUser(authRequest);
            OtpRequest otpRequest = new OtpRequest();
            otpRequest.setEmail(authRequest.getEmail());
            otpService.sendOtp(otpRequest);
            UserDetails userDetails = authService.loadUserByUsername(authRequest.getEmail());
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }


    @PostMapping("/verify-otp")
    @Operation(summary = "Verify OTP for user activation")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpRequest otpRequest) {
        boolean isValid = otpService.verifyOtp(otpRequest);
        if (isValid) {
            authService.enableUserByEmail(otpRequest.getEmail());
            return ResponseEntity.ok("OTP verified. User activated.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
    }


    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestBody GenerateOtpRequest otpRequest) {
        otpService.generateAndSendOtp(otpRequest);
        return ResponseEntity.ok("OTP resent successfully.");
    }


    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
            User user = authRepository.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found after authentication."));

            if (!user.isEnabled()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account not verified. Please verify your email using the OTP.");
            }
            UserDetails userDetails = authService.loadUserByUsername(authRequest.getEmail());
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Invalid email or password.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }


    @PostMapping("/logout")
    @Operation(summary = "Logout user", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<?> logout() {
        try {
            SecurityContextHolder.clearContext();
            return ResponseEntity.ok().body("Logout successful. Please remove the token from client storage.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Logout failed: " + e.getMessage());
        }
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(ResetPasswordRequest resetPasswordRequest) {
        String email = resetPasswordRequest.getEmail();
        GenerateOtpRequest generateOtpRequest = new GenerateOtpRequest(email);
        generateOtpRequest.setEmail(email);
        otpService.generateAndSendOtp(generateOtpRequest);
        return ResponseEntity.ok("OTP sent to your email");
    }


    @PostMapping("/verify-reset-otp")
    public ResponseEntity<String> verifyResetOtp(@RequestBody OtpRequest otpRequest) {
        boolean isValid = otpService.verifyOtp(otpRequest);

        if (isValid) {
            return ResponseEntity.ok("OTP verified");
        } else {
            return ResponseEntity.ok("Invalid or expired OTP");
        }
    }


    @PutMapping("/reset-new-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetRequest) {
        if (!resetRequest.getNewPassword().equals(resetRequest.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }
        User user = authService.findUserByEmail(resetRequest.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        User updated = authService.resetPassword(resetRequest.getEmail(), resetRequest.getNewPassword());
        return ResponseEntity.ok(updated);
    }
}