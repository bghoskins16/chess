package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;

public interface GameDataAccess {

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear();

    //createGame: Create a new game.
    public int createGame(String gameName);

    //getGame: Retrieve a specified game with the given game ID.
    public GameData getGame(int gameID);

    //listGames: Retrieve all games.
    public Collection<GameData> listGames();

    //updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID. This is used when a move is made.
    public void updateGame(int gameID, ChessGame newGame);

    //addUser: Used when players join a game
    public void addUserWhite(int gameID, String username);

    public void addUserBlack(int gameID, String username);
}
