package com.example.demo.game.card;

public abstract class Card {
    private int id;
    private String text;

    public String getText() { return text; }

    public void setText(String text) {
        this.text = text;
    }
}
