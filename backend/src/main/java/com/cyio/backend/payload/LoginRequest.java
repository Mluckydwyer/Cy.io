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
    public String getPassword() {
        return password;
    }
}
