package com.funkypanda.hiring.model;

import com.funkypanda.hiring.RouletteConfiguration;
import com.funkypanda.hiring.enums.Players;

public class Player {

	public Player(String playerId) {
		this.playerId = playerId;
		this.currency = RouletteConfiguration.getInitialCreditAmount();
		Players.players.addPlayer(this);
	}

	private String playerId;
	private String roomId;
	private int currency;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public int getCurrency() {
		return currency;
	}

	public int addCurrency(int amount) {
		return currency += amount;
	}

	public int removeCurrency(int amount) {
		return currency -= amount;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}
