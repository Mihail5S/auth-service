package com.example.program.auth.dto;

public class AuthResponse {
    private Long userId;
    private String email;
    private String accessToken;

    public AuthResponse(Long userId, String email, String accessToken) {
        this.userId = userId;
        this.email = email;
        this.accessToken = accessToken;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
