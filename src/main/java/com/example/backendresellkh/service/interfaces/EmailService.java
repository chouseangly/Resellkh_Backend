package com.example.backendresellkh.service.interfaces;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
//    void sendOtpEmail(String toEmail, String otp, String purpose);
//    void sendSimpleOtpEmail(String toEmail, String otp, String purpose);
}