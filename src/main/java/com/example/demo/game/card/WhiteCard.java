package com.example.demo.game.card;

import java.util.ArrayList;
import java.util.List;

public class WhiteCard extends Card{
    private List<String> whiteCards = new ArrayList<>();

    private String text;

    public WhiteCard(String text) {
        setText(text);
        addCardValues();
    }

    public void addCardValues() {
        whiteCards.add("Flying sex snakes.");
        whiteCards.add("Michelle Obama`s arms.");
        whiteCards.add("German dungeon porn.");
        whiteCards.add("White people.");
        whiteCards.add("Getting so angry that you pop a boner.");
        whiteCards.add("Tasteful sideboob.");
        /*        "Praying the gay away.",
                "Two midgets shitting into a bucket.",
                "MechaHitler.",
                "Being a motherfucking sorcerer.",
                "A disappointing birthday party.",
                "Puppies!",
                "A windmill full of corpses.",
                "Guys who don`t call.",
                "Racially-biased SAT questions.",
                "Dying.",
                "Steven Hawking talking dirty.",
                "Being on fire.",
                "A lifetime of sadness.",
                "An erection that lasts longer than four hours.",
                "AIDS.",
                "Same-sex ice dancing.",
                "Glenn Beck catching his scrotum on a curtain hook.",
                "The Rapture.",
                "Pterodactyl eggs.",
                "Crippling debt.",
                "Eugenics.",
                "Exchanging pleasentries.",
                "Dying of dysentry.",
                "Roofies.",
                "Getting naked and watching Nickelodeon.",
                "The forbidden fruit.",
                "Republicans.",
                "The Big Bang.",
                "Anal beads.",
                "Amputees.",
                "Men.",
                "Surprise sex!",
                "Kim Jong-il.",
                "Concealing a boner.",
                "Agriculture.",
                "Glenn Beck being harried by a swarm of buzzards.",
                "Making a pouty face.",
                "A salty surprise.",
                "The Jews.",
                "Charisma.",
                "YOU MUST CONDUCT ADDITIONAL PYLONS.",
                "Panda sex.",
                "Taking off your shirt.",
                "A drive-by shooting.",
                "Ronald Reagan.",
                "Morgan Freeman`s voice.",
                "Breaking out into song and dance.",
                "Jewish fraternities.",
                "Dead babies.",
                "Masturbation.",
                "Hormone injections.",
                "All-you-can-eat shrimp for $4.99.",
                "Incest.",
                "Scalping."*/
    }

    public String getText() {
        if (whiteCards.size() > 1) {
            return whiteCards.remove(0);
        } else {
            System.out.println("There are no more white cards left!");
            return "";
        }
    }

    public void setText(String text) {
        this.text = text;
    }
}
