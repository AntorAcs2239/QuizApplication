package com.example.myquiz;

public class leaderboardItems {
    String username;
    long coins;

    public leaderboardItems() {
    }

    public leaderboardItems(String username, long coins) {
        this.username = username;
        this.coins = coins;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }
}
