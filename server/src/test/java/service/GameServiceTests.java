package service;

import chess.ChessGame;
import dataaccess.AuthMemoryDataAccess;
import dataaccess.GameMemoryDataAccess;
import dataaccess.UserMemoryDataAccess;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;
import server.ResponseException;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTests {


    static GameService service = new GameService();
    static UserMemoryDataAccess userDatabase = new UserMemoryDataAccess();
    static AuthMemoryDataAccess authDatabase = new AuthMemoryDataAccess();
    static GameMemoryDataAccess gameDatabase = new GameMemoryDataAccess();

    UserData user;
    AuthData auth;

    @BeforeEach
    void clear() {
        service.clear();
        user = new UserData("user1", "pass1", "mail1");
        userDatabase.createUser(user);
        auth = authDatabase.createAuth(user.username());
    }

    @Test
    void createGameGood() {
        CreateGameRequest game = new CreateGameRequest(auth.authToken(), "game1");
        int gameID;

        try {
            gameID = service.createGame(game);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Collection<GameData> gameList = gameDatabase.listGames();
        assertEquals(1, gameList.size());
        assertEquals(gameDatabase.getGame(gameID).gameName(), "game1");
    }

    @Test
    void createGameBad() {
        CreateGameRequest game = new CreateGameRequest(auth.authToken(), null);

        try {
            service.createGame(game);
        } catch (ResponseException e) {
            assertEquals(e.statusCode(), 400);
        }

        Collection<GameData> gameList = gameDatabase.listGames();
        assertEquals(0, gameList.size());
    }

    @Test
    void listGamesGood() {
        gameDatabase.createGame("game1");
        gameDatabase.createGame("game2");
        gameDatabase.createGame("game3");
        gameDatabase.createGame("game4");

        ListGamesRequest listGamesRequest = new ListGamesRequest(auth.authToken());
        Collection<GameData> gameListReceived;

        try {
            gameListReceived = service.listGames(listGamesRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Collection<GameData> gameListExpected = gameDatabase.listGames();
        assertEquals(gameListReceived, gameListExpected);
    }

    @Test
    void listGamesBad() {
        gameDatabase.createGame("game1");
        gameDatabase.createGame("game2");
        gameDatabase.createGame("game3");
        gameDatabase.createGame("game4");

        ListGamesRequest listGamesRequest = new ListGamesRequest("Bad Auth Token");
        Collection<GameData> gameListReceived = null;

        try {
            gameListReceived = service.listGames(listGamesRequest);
        } catch (ResponseException e) {
            assertEquals(e.statusCode(), 401);
        }

        assertNull(gameListReceived);
    }

    @Test
    void joinGameGood() {
        int gameID = gameDatabase.createGame("game1");

        JoinGameRequest joinGameRequest = new JoinGameRequest(auth.authToken(), ChessGame.TeamColor.WHITE, gameID);

        try {
            service.joinGame(joinGameRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(gameDatabase.getGame(gameID).whiteUsername(), user.username());
    }

    @Test
    void joinGameBad() {
        int gameID = gameDatabase.createGame("game1");
        GameData gameData = gameDatabase.getGame(gameID);
        gameDatabase.addUserWhite(gameID, "Another Player");
        gameDatabase.addUserBlack(gameID, "And his friend");

        JoinGameRequest joinGameRequest = new JoinGameRequest(auth.authToken(), ChessGame.TeamColor.WHITE, gameID);

        try {
            service.joinGame(joinGameRequest);
        } catch (ResponseException e) {
            assertEquals(e.statusCode(), 403);
        }

        assertNotEquals(gameDatabase.getGame(gameID).whiteUsername(), user.username());
    }

    @Test
    void testClear() {
        gameDatabase.createGame("game1");
        gameDatabase.createGame("game2");
        gameDatabase.createGame("game3");
        gameDatabase.createGame("game4");

        service.clear();

        assertEquals(0, gameDatabase.listGames().size());
    }

}
