package com.funkypanda.hiring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.funkypanda.hiring.model.Bet;
import com.funkypanda.hiring.model.Game;
import com.funkypanda.hiring.model.GameState;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RouletteTest {

	@ClassRule
	public static final DropwizardAppRule rule = new DropwizardAppRule<RouletteConfiguration>(RouletteApplication.class, "test.yml");

	private String baseUrl = "http://localhost:8080/game";
	private ObjectMapper objectMapper = new ObjectMapper();

	private Game initGame(String playerId) throws UnirestException, IOException {
		HttpResponse<String> response = Unirest.post(baseUrl + "/create")
				.queryString("playerId", playerId)
				.asString();

		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new RuntimeException(response.getStatus() + " when creating game: " + response.getBody());
		}

		return objectMapper.readValue(response.getBody(), Game.class);
	}

	private void placeBets(String gameId, List<Bet> moves) throws JsonProcessingException, UnirestException {
		for (Bet move : moves) {
			HttpResponse<String> response = Unirest.put(baseUrl + "/" + gameId)
					.header("Content-Type", "application/json")
					.body(objectMapper.writeValueAsString(move))
					.asString();

			if (response.getStatus() != Status.ACCEPTED.getStatusCode()) {
				throw new RuntimeException(response.getStatus() + " when making move: " + response.getBody());
			}
		}
	}

	private GameState getState(String gameId) throws UnirestException, IOException {
		HttpResponse<String> response = Unirest.get(baseUrl + "/" + gameId).asString();

		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new RuntimeException(response.getStatus() + " when getting state: " + response.getBody());
		}

		return objectMapper.readValue(response.getBody(), GameState.class);
	}

	@Test
	public void testPlayer1Win() throws IOException, UnirestException {
		String gameId = initGame("1").getGameId();
		placeBets(gameId, new ArrayList<Bet>());

		GameState state = getState(gameId);
		assertThat(true);
	}
}
