package com.funkypanda.hiring;

import io.dropwizard.Configuration;

public class RouletteConfiguration extends Configuration {
    private static final int maxRoomPlayers = 4;
    private static final int initialCreditAmount = 100;


    public static int getMaxRoomPlayers() {
        return maxRoomPlayers;
    }


    public static int getInitialCreditAmount() {
        return initialCreditAmount;
    }
}
