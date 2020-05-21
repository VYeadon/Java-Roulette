package com.funkypanda.hiring.enums;


import com.funkypanda.hiring.model.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public enum Games {
    /**
     * Easy lazy way to hold global list of games.
     * Singleton may be preferable, but init is lazy so thread safe
     */
    games;

    private List<Game> listOfGames;

    Games() {
        listOfGames = new ArrayList<Game>();
    }

    public void addGame(Game game) {
        this.listOfGames.add(game);
    }

    public List<Game> getListOfGames() {
        return listOfGames;
    }

    public Game getGameFromId(String gameId) {
        List<Game> games = listOfGames.stream()
                .filter(game -> game.getGameId().equals(gameId))
                .collect(Collectors.toList());

        if (games.size() > 0) {
            return games.get(0);
        }
        return null;
    }

}