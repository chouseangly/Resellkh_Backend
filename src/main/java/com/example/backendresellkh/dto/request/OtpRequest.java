package com.example.backendresellkh.dto.request;

import lombok.Data;

@Data
public class OtpRequest {
    private String email;
    private String otpCode;
}
