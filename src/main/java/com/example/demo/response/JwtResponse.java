package com.example.demo.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private int userId;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, int userId, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }


}