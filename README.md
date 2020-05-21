Roulette Test
===================

The task is to implement the server side of a multiplayer
[roulette](https://en.wikipedia.org/wiki/Roulette) game.

We've provided a skeleton Scala Dropwizard app that defines some of the API but lacks any implementation.
Please feel free to complete the task in Java or Scala using the code provided as a starting point.

Please provide information with anything you'd like us to know about your submission (setup
instructions, technical justifications, future improvements etc.) in the Candidate Comments at the bottom of this file.

It is not required to set up any databases etc in order to persist data.

Rules
-----

Players can join or create Roulette rooms. Any player in a room can place bets. Only the room creator can spin the wheel.
If a creator leaves the room then the room is closed. Players are also free to leave any room they are in which will remove any unplayed bets they have placed.
A room can have a maximum of four players. Players can only join one room at a time.

New players are credited 100 currency when they first join or create a game. There is no other way for a player to deposit more currency. Players currencies should move with them if they move from one room to another.

API
---

Please ensure that your application meets this API.  The RouletteResource and RouletteTest classes provided should make it
clear how to achieve this.  Please return appropriate http error codes to enforce both the rules described above and
the restrictions described below.

### Create a room ###

    method                : POST
    url                   : /game/create?playerId=<id of player>
    example response body : {"gameId": "<id of the game>"}

The client will provide the id of the player creating the room. An appropriate error code should be returned if a player tries to create a  new room when they are currently in another room. The response should be a json object containing a single field, the id that
identifies the new game.

### Join a room ###

    method                : POST
    url                   : /game/join?playerId=<id of player>&gameId=<id of game>
    example response body : {"success": <true or false>}

The client will provide the id of the room being joined and of the player joining the room. An appropriate error code should be returned if a player tries to join a new room when they are currently in another room. The response should be a json object containing a single field specifying if the join was successful.

### Leave a room ###

    method                : POST
    url                   : /game/leave?playerId=<id of player>&gameId=<id of game>
    example response body : <empty>

The client will provide the id of the room being left and of the player leaving the room.

### Make a bet ###

    method                : PUT
    url                   : /game/<id of the game>
    example request body  : {"playerId": "<id of player making the move>", "betAmount": <amount the player is betting>, , "betType": "<describes the bet type e.g. row, column, redorblack, oddoreven, number>", "betTypeValue": <number describing the specific bet in that bet type e.g. if the betType is number then any number 0-36 or if the type is redorblack then 0 or 1 respectively>}
    example response body : <empty>

The \<id of the game> will be the id of a game previously created via a call to the *Create a room* endpoint.  The player id
will be the id of the player making the bet. An appropriate error code should be returned if a player attempts to make an invalid bet or the room is not available to the player.

### Spin the wheel ###

    method                : PUT
    url                   : /game/spin/<id of the game>
    example request body  : {"playerId": "<id of player making the spin>"}
    example response body : {"result": <number result>}

The client will provide the id of the room with the wheel to spin in and of the player spinning the wheel. An appropriate error code should be returned if a player tries to spin a wheel they are not entitled to. The response should be a json object containing a single field specifying the numerical result of the spin. This should also handle paying out all winnings by updating players respective data.

### Get the game state ###

    method                : GET
    url                   : /game/<id of the game>
    example response body : {"open": <true or false>, "lastResult": <number specifying last result (-1 for rooms without result)>, "numberOfMembers": <number of players in the room>, "lastResultTime": <time of last result in unix time (-1 for rooms without result)>}

The \<id of the game> will be the id of a game previously created via a call the *Create a game* endpoint. The response should be a json object containing a description of the room and it's last result as described in the example response.


Candidate Comments
------------------

    I have used enums as an easy way to keep track of all players and games however if more functionality was required such as placing games in rooms of games etc. I would pull these out into larger classes.
    
    Tests need to be written but nothing in the requirements for them to be included so i have opted out.
    Would add them if this was in production. 
    Health checks also need to be added which are lightweight tests to ensure the server is running in production.
    
    I should refactor out the checking of of game and player exisiting when getting one from id to remove repeating code and make a more robust and consistent error system.
    
    Needs a validity checker for each betType and betRange but needed to finish this as i have a busy upcoming week, sorry
    
    I couldn't find the odds for the rows betType so have ommited this.
    
    Java is not the main language i work in and i am unfamilar with dropwizard so forgive me if the code structuring is wrong 