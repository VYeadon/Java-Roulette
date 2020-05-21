package com.funkypanda.hiring.model;

import com.funkypanda.hiring.RouletteConfiguration;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    public GameState(Player creator) {
        this.members = new ArrayList<Player>();
        this.bets = new ArrayList<Bet>();
        this.creator = creator;
    }

    private boolean open;
    private int lastResult = -1;
    private long lastResultTime = -1;

    private final Player creator;
    private final List<Player> members;
    private List<Bet> bets;

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public void processBets() {
        this.bets.forEach(bet -> bet.playBet(this.lastResult));
        this.clearBets();
    }

    public boolean isOpen() {
        return this.getNumberOfMembers() < RouletteConfiguration.getMaxRoomPlayers();
    }

    public void setLastResult(int lastResult) {
        this.lastResult = lastResult;
    }

    public int getNumberOfMembers() {
        return members.size();
    }

    public void setLastResultTime(long time) {
        this.lastResultTime = time;
    }

    public List<Player> getMembers() {
        return members;
    }

    protected void addMember(Player member) {
        this.members.add(member);
    }

    protected void removeMember(Player member) {
        this.members.remove(member);
    }

    public Player getCreator() {
        return creator;
    }

    private void clearBets() {
        bets = new ArrayList<Bet>();
    }

    public void addBet(Bet bet) {
        this.bets.add(bet);
    }

    public void setOpen(boolean status) {
        this.open = status;
    }

    public boolean getOpen() {
        return this.open;
    }

    public int getLastResult() {
        return lastResult;
    }

    public long getLastResultTime() {
        return lastResultTime;
    }
}
