package com.example.demo.game.card;

import java.util.ArrayList;
import java.util.List;

public class BlackCard extends Card{

    private List<String> blackCards = new ArrayList<>();

    public BlackCard(String text) {
        this.setText(text);
        addCardValues();
    }

    private void addCardValues() {
        blackCards.add("__________? There`s an app for that.");
        blackCards.add("Why can`t I sleep at night?");
        blackCards.add("What`s that smell?");
        blackCards.add("I got 99 problems but __________ aint one.");
        blackCards.add("Maybe she`s born with it. Maybe it`s __________.");
        blackCards.add("What`s the next Happy Meal toy?");
        /*        "It`s a pity that kids these days are all getting involved with __________.",
                "What`s that sound?",
                "What ended my last relationship?",
                "I drink to forget __________.",
                "What is Batman	s guilty pleasure?",
                "What`s a girl`s best friend?",
                "TSA guidelines now prohibit __________ on airplanes.",
                "__________. That`s how I want to die.",
                "What`s the most emo?",
                "__________. High five, bro!",
                "White people like __________.",
                "BILLY MAYS HERE FOR __________"*/
    }

    public String getText() {
        if (blackCards.size() > 1) {
            return blackCards.remove(0);
        } else {
            System.out.println("There are no more white cards left!");
            return "";
        }
    }
}
