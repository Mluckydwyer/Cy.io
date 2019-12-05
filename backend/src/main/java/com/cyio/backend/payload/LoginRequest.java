package com.cyio.backend.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    public String getuserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
