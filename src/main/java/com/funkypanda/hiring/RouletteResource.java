package com.funkypanda.hiring;

import com.funkypanda.hiring.constants.BetOdds;
import com.funkypanda.hiring.enums.Games;
import com.funkypanda.hiring.enums.Players;
import com.funkypanda.hiring.model.Bet;
import com.funkypanda.hiring.model.Game;
import com.funkypanda.hiring.model.GameState;
import com.funkypanda.hiring.model.Player;
import com.funkypanda.hiring.resource.json.SpinBody;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RouletteResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response createGame(@QueryParam("playerId") String playerId) {
        Player player = Players.players.getPlayerFromId(playerId);
        if (player == null) {
            player = new Player(playerId);
        }

        if (player.getRoomId() != null) {
            throw new WebApplicationException("Player cannot create room as they are already in one.", Status.CONFLICT);
        }
        Game game = new Game(player);
        game.addMember(player);

        JSONObject responseJson = new JSONObject();
        responseJson.put("GameId", game.getGameId());
        return Response.status(Status.OK).entity(responseJson.toString()).build();
    }


    @POST
    @Path("/join")
    public Response joinRoom(@QueryParam(value = "playerId") String playerId, @QueryParam(value = "gameId") String gameId) {
        Game game = Games.games.getGameFromId(gameId);
        if (game == null) {
            throw new WebApplicationException("Cannot join game as no game exists with the gameId.", Status.NOT_FOUND);
        }

        Player player = Players.players.getPlayerFromId(playerId);
        if (player == null) {
            player = new Player(playerId);
        }
        if (player.getRoomId() != null) {
            throw new WebApplicationException("Player cannot join room as they are already in one.", Status.BAD_REQUEST);
        }

        boolean playerAdded = game.addMember(player);

        JSONObject responseJson = new JSONObject();
        responseJson.put("success", playerAdded);

        return Response.status(Status.OK).entity(responseJson.toString()).build();
    }


    @POST
    @Path("/leave")
    public void leaveRoom(@QueryParam("playerId") String playerId, @QueryParam("gameId") String gameId) {
        Game game = Games.games.getGameFromId(gameId);
        if (game == null) {
            throw new WebApplicationException("No game found with playerId.", Status.NOT_FOUND);
        }

        Player player = Players.players.getPlayerFromId(playerId);
        if (player == null) {
            throw new WebApplicationException("No player found with playerId.", Status.NOT_FOUND);
        }

        if (!game.getGameState().getMembers().contains(player)) {
            throw new WebApplicationException("Player cannot leave given gameId as they are not part of the game.", Status.NOT_FOUND);
        }

        game.removeMember(player);
    }


    @PUT
    @Path("/{gameId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void makeBet(@PathParam("gameId") String gameId, Bet bet) {
        Game game = Games.games.getGameFromId(gameId);
        if (game == null) {
            throw new WebApplicationException("No game found with playerId.", Status.NOT_FOUND);
        }

        Player player = Players.players.getPlayerFromId(bet.getPlayerId());
        List<Player> players = game.getGameState().getMembers();
        if (!players.contains(player)) {
            throw new WebApplicationException("Player is not part of the given game id.", Status.BAD_REQUEST);
        }

        if(!BetOdds.betTypes.contains(bet.getBetType())) {
            throw new WebApplicationException("Invalid betType.", Status.BAD_REQUEST);
        }

        if (!bet.isBetValid()) {
            throw new WebApplicationException("Bet amount is not valid. User does not have enough currency to place bet.", Status.BAD_REQUEST);
        }

        game.addBet(bet);
        player.removeCurrency(bet.getBetAmount());
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/spin/{gameId}")
    public int spinWheel(@PathParam("gameId") String gameId, SpinBody json) {
        Game game = Games.games.getGameFromId(gameId);
        if (game == null) {
            throw new WebApplicationException("No game found with playerId.", Status.NOT_FOUND);
        }

        Player player = Players.players.getPlayerFromId(json.playerId);

        if (game.getGameState().getCreator() != player) {
            throw new WebApplicationException("User cannot spin this rooms wheel as they are not the creator.", Status.BAD_REQUEST);
        }

        return game.spinTheWheel();
    }

    @GET
    @Path("/{gameId}")
    public GameState getGame(@PathParam("gameId") String gameId) {
        Game game = Games.games.getGameFromId(gameId);
        if (game == null) {
            throw new WebApplicationException("No game found with playerId.", Status.NOT_FOUND);
        }
        return game.getGameState();
    }
}