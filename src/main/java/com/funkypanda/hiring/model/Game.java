package com.funkypanda.hiring.model;

import com.funkypanda.hiring.enums.Games;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Game {

    private String gameId;


    public Game(Player creator) {
        gameId = Integer.toString(System.identityHashCode(this));
        this.gameState = new GameState(creator);
        this.addMember(creator);
        Games.games.addGame(this);
    }

    private GameState gameState;


    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int spinTheWheel() {
        int result = ThreadLocalRandom.current().nextInt(1, 21 + 1);
        this.gameState.setLastResult(result);
        this.gameState.setLastResultTime(new Date().getTime());
        this.gameState.processBets();
        return result;
    }

    public GameState getGameState() {
        return gameState;
    }


    public boolean addMember(Player member) {
        if (this.gameState.isOpen() && member.getRoomId() == null) {
            this.gameState.addMember(member);
            member.setRoomId(this.gameId);
            return true;
        }
        return false;
    }

    public void removeMember(Player player) {
        this.gameState.removeMember(player);
        player.setRoomId(null);

        if(player==this.gameState.getCreator()) {
            this.gameState.setOpen(false);
        }

        this.removePlacedPlayerBets(player);
    }

    private void removePlacedPlayerBets(Player player) {
        List<Bet> gameBets = this.gameState.getBets();
        List<Bet> playerBets = new ArrayList<Bet>(gameBets).stream().filter(bet -> bet.getPlayerId().equals(player.getPlayerId())).collect(Collectors.toList());
        gameBets.removeAll(playerBets);
        this.gameState.setBets(gameBets);

        playerBets.forEach(bet -> player.addCurrency(bet.getBetAmount()));
    }

    public void addBet(Bet bet) {
        this.gameState.addBet(bet);
    }

}

