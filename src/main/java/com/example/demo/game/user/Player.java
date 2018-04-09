package com.example.demo.game.user;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String userName;
    private List<String> playerCards = new ArrayList<>();
    private boolean hasPlayed = false;
    private int points = 0;
    private Role role = Role.CARD_PLAYER;

    public Player(String userName) {
        this.userName = userName;
    }

    public enum Role{
        CARD_PLAYER, CARD_CZAR
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getPoints() {
        return points;
    }

    public void addPoint() {
        points++;
    }

    public List<String> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(List<String> playerCards) {
        this.playerCards = playerCards;
    }

    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    public boolean hasPlayed() {
        return hasPlayed;
    }
}
