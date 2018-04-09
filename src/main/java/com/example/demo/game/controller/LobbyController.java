package com.example.demo.game.controller;

import com.example.demo.game.user.Player;

import java.util.*;
import java.util.stream.Collectors;

public class LobbyController implements Runnable {
    private Map<String, Player> players = new HashMap<>();
    private List<String> playerNames = new ArrayList<>();
    private List<String> playedCards = new ArrayList<>();
    private Map<String, String> playedCardMap = new HashMap<>();
    private GameState gameState = GameState.WAIT;
    private Map<String, Integer> playerRoles = new HashMap<>();
    private String cardCzar = "";
    private boolean roundStarted = false;
    private int roundCount = 0;
    private String currentQuestion = "";
    private String gameTitle = "";
    private String winnerCard = null;
    private Player lobbyOwner = null;
    private boolean isStarted = false;


    private enum GameState {
        WAIT, ROUND, CHOOSING, END
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void start() {
        isStarted = true;
    }

    void addUser(String userName) {
        if (!players.containsKey(userName)) {
            playerNames.add(userName);
            players.put(userName, new Player(userName));
            gameTitle = "Waiting for players (" + playerNames.size() + "/3)";
            if (roundCount == 0 && players.keySet().size() == 1) {
                players.get(userName).setRole(Player.Role.CARD_CZAR);
                lobbyOwner = players.get(userName);
            }
            if (gameState == GameState.WAIT) {
                players.get(userName).setPlayerCards(new ArrayList<>());
            } else {
                players.get(userName).setPlayerCards(Arrays.asList("Kek", "Michel Jackson", "Czenu"));
            }
        }
    }

    public LobbyController() {

    }

    public boolean isLobbyOwner(String name) {
        return name.equals(lobbyOwner.getUserName());
    }

    public List<String> getPlayedCards() {
        if (playedCards.size() != players.keySet().size() - 1 && gameState != GameState.END) {
            List<String> tempList = new ArrayList<>();
            for (int i = 0; i < playedCards.size(); i++) {
                tempList.add(" ");
            }
            return tempList;
        }
        return playedCards;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public List<String> getPlayerNames() {
        return players.values().stream().map(Player::getUserName).collect(Collectors.toList());
    }

    public List<String> getPlayerCards(String userName) {
        return players.get(userName).getPlayerCards();
    }

    public boolean hasUser(String userName) {
        return players.containsKey(userName);
    }

    boolean hasNotPlayed(String userName) {
        if (hasUser(userName)) {
            return !players.get(userName).hasPlayed();
        }
        return false;
    }

    boolean isCardPlayer(String userName) {
        return players.get(userName).getRole() == Player.Role.CARD_PLAYER;
    }

    void playCard(String userName, int cardIndex) {
        if (hasUser(userName) && hasNotPlayed(userName) && isCardPlayer(userName)) {
            if (cardIndex >= 0 && cardIndex < players.get(userName).getPlayerCards().size()) {
                playedCards.add(players.get(userName).getPlayerCards().get(cardIndex));
                playedCardMap.put(players.get(userName).getPlayerCards().get(cardIndex), userName);
                List<String> tempList = new ArrayList<>(players.get(userName).getPlayerCards());
                tempList.remove(cardIndex);
                players.get(userName).setPlayerCards(tempList);
                players.get(userName).setHasPlayed(true);
            }
        }
    }

    public Integer getPlayerRole(String userName) {
        return players.get(userName).getRole() == Player.Role.CARD_PLAYER ? 0 : 1;
    }

    public GameState getGameState() {
        return gameState;
    }

    public String getQuestion() {
        return currentQuestion;
    }

    void startRound() {
        winnerCard = null;
        currentQuestion = "Who killed Roger Rabbit?";
        playedCards = new ArrayList<>();
        playedCardMap = new HashMap<>();
        for (Player player : players.values()) {
            player.setHasPlayed(false);
            List<String> tempList = new ArrayList<>(player.getPlayerCards());
            if (roundCount != 0) {
                tempList.add("TEMP");
            }
            player.setPlayerCards(tempList);
            player.setRole(Player.Role.CARD_PLAYER);
            player.setHasPlayed(false);

            roundStarted = true;
        }
        if (cardCzar == null || playerNames.indexOf(cardCzar) == playerNames.size() - 1) {
            players.get(playerNames.get(0)).setRole(Player.Role.CARD_CZAR);
            cardCzar = playerNames.get(0);
        } else {
            players.get(playerNames.get(playerNames.indexOf(cardCzar) + 1)).setRole(Player.Role.CARD_CZAR);
            cardCzar = playerNames.get(playerNames.indexOf(cardCzar) + 1);
        }
        System.out.println(playerRoles);
    }

    void chooseWinner(String userName, int cardIndex) {
        if (winnerCard == null && gameState == GameState.CHOOSING && players.get(userName).getRole() == Player.Role.CARD_CZAR && cardIndex >= 0 && cardIndex < playedCards.size()) {
            winnerCard = playedCards.get(cardIndex);
        }
    }

    @Override
    public void run() {
        while(true) {
            switch(gameState) {
                case WAIT:
                    gameTitle = "Waiting for players (" + playerNames.size() + "/3)";
                    if (players.keySet().size() >= 3) {
                        gameState = GameState.ROUND;
                        for (Player player : players.values()) {
                            player.setPlayerCards(Arrays.asList("Kek", "Michel Jackson", "Czenu"));
                        }
                        winnerCard = null;
                    } else {
                        try {
                            Thread.sleep(20);
                            continue;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case ROUND:
                    if (!roundStarted) {
                        startRound();
                    } else if (roundStarted) {
                        gameTitle = "Choosing time (" + playedCards.size() + "/" + (playerNames.size() - 1) + ")";
                        if (playedCards.size() >= playerNames.size() - 1) {
                            System.out.println("Card Czar chooses");
                            gameTitle = "Card Czar is choosing!";
                            gameState = GameState.CHOOSING;
                        }
                    }
                    break;

                case CHOOSING:
                    if (winnerCard != null) {
                        gameState = GameState.END;
                    }
                    break;

                case END:
                    playedCards = Arrays.asList(winnerCard);
                    gameTitle = "Round has ended. The winner is: " + playedCardMap.get(winnerCard);
                    players.get(playedCardMap.get(winnerCard)).addPoint();
                    try {
                        Thread.sleep(2000);
                        roundCount += 1;
                        gameState = GameState.ROUND;
                        roundStarted = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
