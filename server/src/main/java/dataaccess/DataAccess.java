package dataaccess;
import server.AuthData;
import server.UserData;

public interface DataAccess {

    //clear: A method for clearing all data from the database. This is used during testing.

    //createUser: Create a new user.
    public void createUser(UserData newUser);

    //getUser: Retrieve a user with the given username.
    public UserData getUser(String username);

    //createGame: Create a new game.

    //getGame: Retrieve a specified game with the given game ID.

    //listGames: Retrieve all games.

    //updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID. This is used when players join a game or when a move is made.

    //createAuth: Create a new authorization.
    public AuthData createAuth(String username);

    //getAuth: Retrieve an authorization given an authToken.
    public AuthData getAuth(String authToken);

    //deleteAuth: Delete an authorization so that it is no longer valid.

}
