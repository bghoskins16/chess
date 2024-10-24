package dataaccess;
import model.GameData;
import model.UserData;
import model.AuthData;

import java.util.Collection;

public interface DataAccess{

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear();

    //createUser: Create a new user.
    public void createUser(UserData newUser);

    //getUser: Retrieve a user with the given username.
    public UserData getUser(String username);

    //createGame: Create a new game.
    public int createGame(String gameName);

    //getGame: Retrieve a specified game with the given game ID.
    public GameData getGame(int gameID);

    //listGames: Retrieve all games.
    public Collection<GameData> listGames();

    //updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID. This is used when players join a game or when a move is made.

    //createAuth: Create a new authorization.
    public AuthData createAuth(String username);

    //getAuth: Retrieve an authorization given an authToken.
    public AuthData getAuth(String authToken);

    //deleteAuth: Delete an authorization so that it is no longer valid.
    public void deleteAuth(AuthData authToken);
}
