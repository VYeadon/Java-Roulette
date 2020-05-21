package com.funkypanda.hiring.enums;


import com.funkypanda.hiring.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public enum Players {
    /**
     * Easy lazy way to hold global list of players.
     * Singleton may be preferable, but init is lazy so thread safe
     */
    players;

    private List<Player> listOfPlayers;

    Players() {
        listOfPlayers = new ArrayList<Player>();
    }

    public void addPlayer(Player player) {
        this.listOfPlayers.add(player);
    }

    public List<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public Player getPlayerFromId(String playerId) {
        List<Player> players = listOfPlayers.stream()
                .filter(player -> player.getPlayerId().equals(playerId))
                .collect(Collectors.toList());

        if (players.size() > 0) {
            return players.get(0);
        }
        return null;
    }

}