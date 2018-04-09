package com.example.demo.game.controller;


import com.example.demo.game.game.GameLoop;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.*;

import java.util.*;

@RestController
public class GameLoopController {

    private Map<Integer, LobbyController> gamesInProgress = new HashMap<>();

    @RequestMapping(value="/user/add", method=RequestMethod.GET)
    public void userAdd(@RequestParam(value="lobbyNr") int lobbyNr,
                        @RequestParam(value="userName") String userName) {
        if (!gamesInProgress.containsKey(lobbyNr)) {
            gamesInProgress.put(lobbyNr, new LobbyController());
        }
        if (!gamesInProgress.get(lobbyNr).hasUser(userName)) {
            gamesInProgress.get(lobbyNr).addUser(userName);
            System.out.println("Kasutaja " + userName + " Ühines mänguga!");
        } else {
            System.out.println("Kasutaja " + userName + " Ühines tagasi.");
        }
    }

    @RequestMapping(value="/game/getdeck")
    public String getDeck(@RequestParam(value="lobbyNr") int lobbyNr,
                          @RequestParam(value="userName") String userName) throws JSONException {
        if (!gamesInProgress.containsKey(lobbyNr)) return null;
        return new JSONObject()
                .put("playedCards", gamesInProgress.get(lobbyNr).getPlayedCards())
                .put("playerCards", gamesInProgress.get(lobbyNr).getPlayerCards(userName))
                .put("questionCard", gamesInProgress.get(lobbyNr).getQuestion())
                .put("gameTitle", gamesInProgress.get(lobbyNr).getGameTitle())
                .put("role", gamesInProgress.get(lobbyNr).getPlayerRole(userName)).toString();
    }

    @RequestMapping(value="/game/getroundinfo")
    public String getRoundInfo(@RequestParam(value="lobbyNr") int lobbyNr,
                               @RequestParam(value="userName") String userName) throws JSONException {
        if (!gamesInProgress.containsKey(lobbyNr)) return null;
        JSONObject returnObj = new JSONObject();
        if (!gamesInProgress.get(lobbyNr).getPlayerNames().contains(userName)) {
            return returnObj.toString();
        }
        List<String> playerCards = new ArrayList<>();
        for (String card : gamesInProgress.get(lobbyNr).getPlayerCards(userName)) {
            playerCards.add(card);
        }
        return returnObj
                .put("playerCards", playerCards)
                .put("role", gamesInProgress.get(lobbyNr).getPlayerRole(userName))
                .put("questionCard", gamesInProgress.get(lobbyNr).getQuestion()).toString();
    }

    @RequestMapping(value = "/game/playcard", method=RequestMethod.GET)
    public List<String> playCard(@RequestParam(value="lobbyNr") int lobbyNr,
                                 @RequestParam(value="userName") String userName,
                                 @RequestParam(value="cardIndex") int cardIndex) {
        if (!gamesInProgress.containsKey(lobbyNr)) return null;
        gamesInProgress.get(lobbyNr).playCard(userName, cardIndex);
        return gamesInProgress.get(lobbyNr).getPlayerCards(userName);
    }

    @RequestMapping(value="/game/pickWinner", method=RequestMethod.GET)
    public void pickWinner(@RequestParam(value="lobbyNr") int lobbyNr,
                           @RequestParam(value="userName") String userName,
                           @RequestParam(value="cardIndex") int cardIndex) {
        if (!gamesInProgress.containsKey(lobbyNr)) return;
        gamesInProgress.get(0).chooseWinner(userName, cardIndex);
    }

    @RequestMapping(value="/game/start", method=RequestMethod.GET)
    public void startLobby(@RequestParam(value="lobbyNr") int lobbyNr,
                           @RequestParam(value="userName") String userName) {
        System.out.println(!gamesInProgress.containsKey(lobbyNr) || !gamesInProgress.get(lobbyNr).isLobbyOwner(userName) || gamesInProgress.get(lobbyNr).isStarted());
        if (!gamesInProgress.containsKey(lobbyNr) || !gamesInProgress.get(lobbyNr).isLobbyOwner(userName) || gamesInProgress.get(lobbyNr).isStarted()) return;
        Thread lobbyThread = new Thread(gamesInProgress.get(lobbyNr));
        lobbyThread.start();
    }
}
