package com.example.backendresellkh.service.interfaces;

import com.example.backendresellkh.dto.request.GenerateOtpRequest;
import com.example.backendresellkh.dto.request.OtpRequest;

public interface OtpService {
    void generateAndSendOtp(GenerateOtpRequest request);
    boolean verifyOtp(OtpRequest request);
    void sendOtp(OtpRequest request);

}