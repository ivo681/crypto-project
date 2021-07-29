package com.example.backend.config.security;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private UserPrincipal user;

    public JwtAuthenticationResponse(String accessToken, UserPrincipal user) {
        this.accessToken = accessToken;
        this.user = user;
    }
}
