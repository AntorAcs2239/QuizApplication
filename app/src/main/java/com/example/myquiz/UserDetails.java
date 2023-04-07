package com.example.myquiz;

public class UserDetails {
    private String username,password,email,refercode;
    long coins=500;
    public UserDetails() {
    }
    public UserDetails(String username, String password, String email, String refercode) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.refercode = refercode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefercode() {
        return refercode;
    }

    public void setRefercode(String refercode) {
        this.refercode = refercode;
    }
    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

}
