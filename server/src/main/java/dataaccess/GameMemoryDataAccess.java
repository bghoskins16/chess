package dataaccess;

import chess.ChessGame;
import handler.GameHandler;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class GameMemoryDataAccess implements GameDataAccess {
    static int nextGameID = 1234;
    static private Collection<GameData> games = new ArrayList<>();

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear() {
        games.clear();
    }

    //createGame: Create a new game.
    public int createGame(String gameName) {
        nextGameID++;
        games.add(new GameData(nextGameID, null, null, gameName, new ChessGame()));
        return nextGameID;
    }

    //getGame: Retrieve a specified game with the given game ID.
    public GameData getGame(int gameID) {
        for (GameData game : games) {
            if (game.gameID() == gameID) {
                return game;
            }
        }
        return null;
    }

    //listGames: Retrieve all games.
    public Collection<GameData> listGames() {
        return games;
    }

    //updateGame: Updates a chess game.
    // It should replace the chess game string corresponding to a given gameID.
    // This is used when players join a game or when a move is made.
    public void updateGame(int gameID, ChessGame newGame) {
        GameData oldGameData = this.getGame(gameID);
        GameData newGameData = new GameData(
                oldGameData.gameID(),
                oldGameData.whiteUsername(),
                oldGameData.blackUsername(),
                oldGameData.gameName(),
                newGame
        );
        replaceGame(oldGameData, newGameData);
    }

    public void addUserWhite(int gameID, String username) {
        GameData oldGameData = this.getGame(gameID);
        GameData newGameData = new GameData(oldGameData.gameID(), username, oldGameData.blackUsername(), oldGameData.gameName(), oldGameData.game());
        replaceGame(oldGameData, newGameData);
    }

    public void addUserBlack(int gameID, String username) {
        GameData oldGameData = this.getGame(gameID);
        GameData newGameData = new GameData(oldGameData.gameID(), oldGameData.whiteUsername(), username, oldGameData.gameName(), oldGameData.game());
        replaceGame(oldGameData, newGameData);
    }

    private void replaceGame(GameData oldGameData, GameData newGameData) {
        games.remove(oldGameData);
        games.add(newGameData);
    }
}
