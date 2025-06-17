package com.example.backendresellkh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpResponse {
    private String message;
    private boolean success;

    public OtpResponse(String message) {
        this.message = message;
        this.success = true;
    }
}
