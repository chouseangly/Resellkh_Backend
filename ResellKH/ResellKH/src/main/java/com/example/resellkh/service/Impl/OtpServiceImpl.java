package com.example.resellkh.service.Impl;

import com.example.resellkh.model.dto.OtpRequest;
import com.example.resellkh.model.entity.Otp;
import com.example.resellkh.repository.OtpRepo;
import com.example.resellkh.service.OtpService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpRepo otpRepo;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendOtp(String email) {
        String code = String.format("%06d", new Random().nextInt(1000000));

        Otp otp = new Otp();
        otp.setEmail(email);
        otp.setOtp(code);
        otp.setCreatedAt(LocalDateTime.now());
        otp.setVerified(false);
        otpRepo.saveOtp(otp);

        sendOtpEmail(email, code);
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
                    + "<p>Your OTP code is: " + code + "</p>"
                    + "<p>This code expires in 1 minute.</p>"
                    + "</body></html>";

            helper.setText(htmlContent, true);


            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send OTP email.");
        }
    }

    @Override
    public boolean verifyOtp(OtpRequest request) {
        Otp otp = otpRepo.findLatestByEmail(request.getEmail());

        if (otp == null) {
            System.out.println(" No OTP found for email: " + request.getEmail());
            return false;
        }

        if (otp.isVerified()) {
            System.out.println(" OTP already verified for: " + request.getEmail());
            return false;
        }

        if (otp.getCreatedAt() == null) {
            System.out.println(" OTP created_at is null");
            return false;
        }

        boolean notExpired = otp.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(1));
        boolean matched = otp.getOtp().trim().equals(request.getOtp().trim());

        if (matched && notExpired) {
            otpRepo.markOtpAsVerified(otp.getId());
            return true;
        }

        System.out.println(" OTP did not match or expired for: " + request.getEmail());
        return false;
    }
}
