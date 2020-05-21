package com.funkypanda.hiring.model;

import com.funkypanda.hiring.constants.BetOdds;
import com.funkypanda.hiring.enums.Players;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class Bet {

    public Bet() {}

    public Bet(String playerId, int betAmount, String betType, int betTypeValue) {
        this.playerId = playerId;
        this.betAmount = betAmount;
        this.betType = betType;
        this.betTypeValue = betTypeValue;
    }

    private String playerId;
    private int betAmount;
    private String betType;
    private int betTypeValue;

    public String getPlayerId() {
        return playerId;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public String getBetType() {
        return betType;
    }

    public boolean isBetValid() {
        Player player = Players.players.getPlayerFromId(this.playerId);
        assert player != null;
        return player.getCurrency() > this.betAmount;
    }

    public int playBet(int lastResult) {
        switch (this.betType) {
            case "redorblack":
                return this.handleRedOrBlackBet(lastResult);
            case "oddoreven":
                return this.handleOddOreEvenBet(lastResult);
            case "column":
                return this.handleColumnBet(lastResult);
            case "number":
                return this.handleNumberBet(lastResult);
            default:
                throw new WebApplicationException("Invalid betType supplied", Response.Status.BAD_REQUEST);
        }
    }

    private int handleNumberBet(int lastResult) {
        if(betTypeValue == lastResult) {
            return this.payoutBet(BetOdds.numberOdds);
        }
        return 0;
    }

    private int handleColumnBet(int lastResult) {
        switch (this.betTypeValue) {
            case 0:
                if (BetOdds.column1.contains(lastResult)) {
                    return this.payoutBet(BetOdds.columnOdds);
                }
                return 0;
            case 1:
                if (BetOdds.column2.contains(lastResult)) {
                    return this.payoutBet(BetOdds.columnOdds);
                }
                return 0;
            case 2:
                if (BetOdds.column3.contains(lastResult)) {
                    return this.payoutBet(BetOdds.columnOdds);
                }
                return 0;
            default:
                throw new WebApplicationException("Wrong betTypeValue supplied for redOrBlack betType", Response.Status.BAD_REQUEST);
        }
    }

    private int handleOddOreEvenBet(int lastResult) {
        switch (this.betTypeValue) {
            case 0:
                if ((lastResult & 1) != 0 ) {
                    return this.payoutBet(BetOdds.oddOrEvenOdds);
                }
                return 0;

            case 1:
                if ((lastResult & 1) == 0 ) {
                    return this.payoutBet(BetOdds.oddOrEvenOdds);
                }
                return 0;

            default:
                throw new WebApplicationException("Wrong betTypeValue supplied for redOrBlack betType", Response.Status.BAD_REQUEST);
        }
    }

    private int handleRedOrBlackBet(int lastResult) {
        switch (this.betTypeValue) {
            case 0:
                if (BetOdds.red.contains(lastResult)) {
                    return this.payoutBet(BetOdds.redOrBlackOdds);
                }
                return 0;

            case 1:
                if (BetOdds.black.contains(lastResult)) {
                    return this.payoutBet(BetOdds.redOrBlackOdds);
                }
                return 0;

            default:
                throw new WebApplicationException("Wrong betTypeValue supplied for redOrBlack betType", Response.Status.BAD_REQUEST);
        }
    }

    private int payoutBet(int payoutOdds) {
        Player player = Players.players.getPlayerFromId(this.playerId);
        assert player != null;
        int winAmount = this.betAmount*payoutOdds;
        player.addCurrency(winAmount);
        return winAmount;
    }
}
