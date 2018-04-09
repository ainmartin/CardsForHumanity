package com.example.demo.game.game;

import com.example.demo.game.card.BlackCard;
import com.example.demo.game.card.WhiteCard;
import com.example.demo.game.cardpack.CardPack;

import java.util.ArrayList;
import java.util.List;

public class GameLoop {
    private int cardsInHand;

    private String userName;
    private int state;
    private int role;
    private BlackCard questionCard;
    private WhiteCard yourCard;
    private List<WhiteCard> playedCards = new ArrayList<>();
    private List<WhiteCard> playerCards = new ArrayList<>();
    private List<String> playerHand = new ArrayList<>();
    private List<String> fieldCards = new ArrayList<>();

    private CardPack cardPack = new CardPack();

    public GameLoop(String userName) {
        this.userName = userName;
    }

    public GameLoop(int state, int role, String questionCard, String yourCard, List<String> playedCards,
                    List<String> playerCards) {
        this.state = state;
        this.role = role;
        this.questionCard = new BlackCard(questionCard);
        this.yourCard = new WhiteCard(yourCard);
        playedCards.forEach( (card) -> this.playedCards.add(new WhiteCard(card)));
        playerCards.forEach( (card) -> this.playerCards.add(new WhiteCard(card)));

    }

    public GameLoop(String blackCard, List<String> playerCards, int role) {
        this.questionCard = new BlackCard(blackCard);
        this.playerHand = playerCards;
        this.role = role;
    }

    public GameLoop(List<String> fieldCards) {
        this.fieldCards = fieldCards;
    }

    public void setBoard() {
        //html code goes here?
        // in a for loop
    }

    public void setHand(int amount) {
        for(int i=0; i<amount; i++) {
            addCardToHand();
        }
    }

    public void addCardToHand() {
        playerCards.add(cardPack.getNextWhiteCard());
    }

    public int getState() {
        return state;
    }

    public int getRole() {
        return role;
    }

    public BlackCard getQuestionCard() {
        return questionCard;
    }

    public WhiteCard getYourCard() {
        return yourCard;
    }

    public List<WhiteCard> getPlayedCards() {
        return playedCards;
    }

    public List<WhiteCard> getPlayerCards() {
        return playerCards;
    }
}
