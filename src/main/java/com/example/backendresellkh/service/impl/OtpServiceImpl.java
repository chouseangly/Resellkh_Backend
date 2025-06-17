package com.example.backendresellkh.service.impl;

import com.example.backendresellkh.dto.request.GenerateOtpRequest;
import com.example.backendresellkh.dto.request.OtpRequest;
import com.example.backendresellkh.model.entity.Otp;
import com.example.backendresellkh.repository.OtpRepository;
import com.example.backendresellkh.service.interfaces.AuthService;
import com.example.backendresellkh.service.interfaces.OtpService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private final JavaMailSender mailSender;
    private final AuthService authService;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${otp.length:6}")
    private int otpLength;

    @Value("${otp.validity.minutes:1}")
    private int otpValidityMinutes;

    @Value("${spring.mail.username}")
    private String fromEmail;


    private String generateRandomOtp(int length) {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(secureRandom.nextInt(10));
        }
        return otp.toString();
    }

    private void sendOtpEmail(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Your OTP Code for ResellKH");
            helper.setFrom(fromEmail);

            String htmlContent = "<html><body>"
                    + "<h2 style='color:#333;'>Welcome to <b>ResellKH</b>!</h2>"
                    + "<p>Your OTP code is: <strong>" + code + "</strong></p>"
                    + "<p>This code expires in " + otpValidityMinutes + " minutes.</p>"
                    + "<p>If you did not request this, please ignore this email.</p>"
                    + "</body></html>";

            helper.setText(htmlContent, true); // true for HTML content

            mailSender.send(message);
            System.out.println("OTP email sent successfully to: " + to + " with code: " + code); // For logging
        } catch (Exception e) {
            System.err.println("Failed to send OTP email to " + to + ": " + e.getMessage()); // Log error
            throw new RuntimeException("Failed to send OTP email."); // Re-throw or handle gracefully
        }
    }

    @Override
    public void generateAndSendOtp(GenerateOtpRequest request) {
        String otpCode = generateRandomOtp(otpLength);

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusMinutes(otpValidityMinutes);

        Otp otp = Otp.builder()
                .email(request.getEmail())
                .otpCode(otpCode)
                .createdAt(createdAt)
                .expiresAt(expiresAt)
                .isUsed(false)
                .build();

        otpRepository.save(otp);

        sendOtpEmail(request.getEmail(), otpCode);
    }

    @Override
    public boolean verifyOtp(OtpRequest request) {
        try {
            // 1. Retrieve the most recent UNUSED OTP for the given email and code
            Optional<Otp> otpOpt = otpRepository.findByEmailAndOtpCode(request.getEmail(), request.getOtpCode());

            if (otpOpt.isEmpty()) {
                System.out.println("OTP verification failed: No matching OTP found or already used for email: " + request.getEmail());
                return false;
            }

            Otp otp = otpOpt.get();

            // 2. Check if OTP is expired
        if (otp.isExpired()) {
            System.out.println("OTP verification failed: OTP expired for email: " + request.getEmail());
            otpRepository.markOtpAsUsedByEmail(otp.getEmail()); // Mark as used even if expired
            return false;
        }

            // 3. OTP is valid, mark it as used
            otpRepository.markOtpAsUsedByEmail(otp.getEmail());
            System.out.println("OTP verified successfully for email: " + request.getEmail());

            // 4. Optional: enable user after OTP verification
            // userService.enableUser(request.getEmail());

            return true;
        } catch (Exception e) {
            System.out.println("An error occurred during OTP verification for email: " + request.getEmail());
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void sendOtp(OtpRequest request) {
        String otpCode = generateRandomOtp(otpLength);

        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusMinutes(otpValidityMinutes);

        Otp otp = Otp.builder()
                .email(request.getEmail())
                .otpCode(otpCode)
                .createdAt(createdAt)
                .expiresAt(expiresAt)
                .isUsed(false)
                .build();

        otpRepository.save(otp);

        sendOtpEmail(request.getEmail(), otpCode);
    }

}