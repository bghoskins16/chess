package dataaccess;

import model.UserData;
import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class MemoryDataAccess implements DataAccess{
    final private Collection<UserData> users = new ArrayList<>();
    final private Collection<AuthData> authTokens = new ArrayList<>();

    //clear: A method for clearing all data from the database. This is used during testing.

    //createUser: Create a new user.
    public void createUser(UserData newUser){
        users.add(newUser);
    }

    //getUser: Retrieve a user with the given username.
    public UserData getUser(String username){
        for (UserData user : users) {
            if (user.username().equals(username)){
                return user;
            }
        }
        return null;
    }

    //createGame: Create a new game.

    //getGame: Retrieve a specified game with the given game ID.

    //listGames: Retrieve all games.

    //updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID. This is used when players join a game or when a move is made.

    //createAuth: Create a new authorization.
    public AuthData createAuth(String username){
        AuthData newAuth = new AuthData(username,UUID.randomUUID().toString());
        authTokens.add(newAuth);
        return newAuth;
    }

    //getAuth: Retrieve an authorization given an authToken.
    public AuthData getAuth(String authToken){
        for (AuthData auth : authTokens) {
            if (auth.authToken().equals(authToken)){
                return auth;
            }
        }
        return null;
    }

    //deleteAuth: Delete an authorization so that it is no longer valid.
}
