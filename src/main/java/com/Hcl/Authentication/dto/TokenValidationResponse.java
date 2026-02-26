package com.Hcl.Authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidationResponse {
    private boolean valid;
    private String email;
    private List<String> roles;
    private String message;

    public TokenValidationResponse(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }
}
