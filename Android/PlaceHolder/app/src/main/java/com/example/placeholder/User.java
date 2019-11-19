package com.example.placeholder;

import java.security.AuthProvider;
import java.util.UUID;


public class User {
    private String userid;

    private String userName;

    private String email;

    private String password;

    private AuthProvider provider;

    private String providerId;

    private int gamesOwned;

    public User() {
        UUID newID = UUID.randomUUID(); //generate a random UUID for the new User
        this.userid = newID.toString();
        this.userName = newID.toString();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGamesOwned() {
        return gamesOwned;
    }

    public void setGamesOwned(int gamesOwned) {
        this.gamesOwned = gamesOwned;
    }
}

