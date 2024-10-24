package dataaccess;

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

    //updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID. This is used when players join a game or when a move is made.
}