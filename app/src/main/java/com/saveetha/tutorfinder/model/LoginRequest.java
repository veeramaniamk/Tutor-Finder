package com.saveetha.tutorfinder.model;

public class LoginRequest {
    private String email;
    private String password;
    private String platform;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
