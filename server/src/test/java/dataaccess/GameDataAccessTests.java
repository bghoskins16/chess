package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class GameDataAccessTests {

    static GameDataAccess gameDatabase;

    @BeforeEach
    void clear() {
        try {
            gameDatabase = new GameMySqlDataAccess();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        gameDatabase.clear();

    }

    @Test
    void createGameTest() {
        int gameId;
        GameData gameRecv;
        try {
            gameId = gameDatabase.createGame("game1");
            gameRecv = gameDatabase.getGame(gameId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(gameId, gameRecv.gameID());
        assertEquals("game1", gameRecv.gameName());
        assertNull(gameRecv.whiteUsername());
        assertNull(gameRecv.blackUsername());
        assertEquals(new ChessGame(), gameRecv.game());
    }

    @Test
    void createGameMissingInfoTest() {
        try {
            gameDatabase.createGame(null);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    @Test
    void getGameTest() {
        int gameId1;
        int gameId2;
        int gameId3;
        GameData gameRecv1;
        GameData gameRecv2;
        GameData gameRecv3;
        try {
            gameId1 = gameDatabase.createGame("game1");
            gameId2 = gameDatabase.createGame("game2");
            gameId3 = gameDatabase.createGame("game3");

            gameRecv3 = gameDatabase.getGame(gameId3);
            gameRecv2 = gameDatabase.getGame(gameId2);
            gameRecv1 = gameDatabase.getGame(gameId1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals("game1", gameRecv1.gameName());
        assertEquals("game2", gameRecv2.gameName());
        assertEquals("game3", gameRecv3.gameName());
    }

    @Test
    void getGameNotAdded() {
        GameData gameRecv;

        try {
            gameRecv = gameDatabase.getGame(9999);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertNull(gameRecv);
    }

    @Test
    void listGamesTest() {
        Collection<GameData> games;

        try {
            gameDatabase.createGame("game1");
            gameDatabase.createGame("game2");
            gameDatabase.createGame("game3");

            games = gameDatabase.listGames();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(3, games.size());
    }

    @Test
    void listGamesNone() {
        Collection<GameData> games;

        try {
            games = gameDatabase.listGames();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(0, games.size());
    }


    @Test
    void updateGameTest() {
        int id;
        GameData gameRecv;
        ChessGame editedGame = new ChessGame();
        editedGame.setTeamTurn(ChessGame.TeamColor.BLACK);

        try {
            id = gameDatabase.createGame("game1");

            gameDatabase.addUserWhite(id, "user1");
            gameDatabase.addUserBlack(id, "user2");
            gameDatabase.updateGame(id, editedGame);

            gameRecv = gameDatabase.getGame(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(id, gameRecv.gameID());
        assertEquals("game1", gameRecv.gameName());
        assertEquals("user1", gameRecv.whiteUsername());
        assertEquals("user2", gameRecv.blackUsername());
        assertEquals(editedGame, gameRecv.game());
        assertNotEquals(new ChessGame(), gameRecv.game());
    }

    @Test
    void updateBadGameTest() {
        int id;
        GameData gameRecv;
        ChessGame editedGame = new ChessGame();
        editedGame.setTeamTurn(ChessGame.TeamColor.BLACK);

        try {
            id = gameDatabase.createGame("game1");

            gameDatabase.addUserWhite(id + 1, "user1");
            gameDatabase.addUserBlack(id - 5, "user2");
            gameDatabase.updateGame(id * 3, editedGame);

            gameRecv = gameDatabase.getGame(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(id, gameRecv.gameID());
        assertEquals("game1", gameRecv.gameName());
        assertNotEquals("user1", gameRecv.whiteUsername());
        assertNotEquals("user2", gameRecv.blackUsername());
        assertNotEquals(editedGame, gameRecv.game());
    }

    @Test
    void clearGamesTest() {
        Collection<GameData> games;

        try {
            gameDatabase.createGame("game1");
            gameDatabase.createGame("game2");
            gameDatabase.createGame("game3");

            gameDatabase.clear();

            games = gameDatabase.listGames();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(0, games.size());
    }
}
