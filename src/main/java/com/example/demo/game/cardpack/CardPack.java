package com.example.demo.game.cardpack;

import com.example.demo.game.card.BlackCard;
import com.example.demo.game.card.WhiteCard;

import java.util.ArrayList;
import java.util.List;

public class CardPack {
    private List<WhiteCard> whiteCards = new ArrayList<>();
    private List<BlackCard> blackCards = new ArrayList<>();

    public void addWhiteCardToPack(WhiteCard card) {
        whiteCards.add(card);
    }

    public void addBlackCardToPack(BlackCard card) {
        blackCards.add(card);
    }

    public WhiteCard getNextWhiteCard() {
        if (whiteCards.size() > 1) {
            return whiteCards.remove(0);
        } else {
            System.out.println("There are no more white cards left!");
            return new WhiteCard("");
        }

    }

    public BlackCard getNextBlackCard() {
        if (whiteCards.size() > 1) {
            return blackCards.remove(0);
        } else {
            System.out.println("There are no more black cards left!");
            return new BlackCard("");
        }

    }
}
