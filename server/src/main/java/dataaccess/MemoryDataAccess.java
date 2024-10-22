package dataaccess;

import chess.ChessGame;
import model.UserData;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class MemoryDataAccess implements DataAccess{
    final private Collection<UserData> users = new ArrayList<>();
    final private Collection<AuthData> authTokens = new ArrayList<>();
    final private Collection<GameData> games = new ArrayList<>();

    int nextGameID = 0;

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear(){
        users.clear();
        authTokens.clear();
        games.clear();
    }

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
    public int createGame(String gameName){
        nextGameID++;
        games.add(new GameData(nextGameID, "", "", gameName, new ChessGame()));
        return nextGameID;
    }

    //getGame: Retrieve a specified game with the given game ID.
    public GameData getGame(int gameID){
        for (GameData game : games) {
            if (game.gameID() == gameID){
                return game;
            }
        }
        return null;
    }

    //listGames: Retrieve all games.
    public Collection<GameData> listGames(){
        return games;
    }

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
    public void deleteAuth(AuthData authToken){
        authTokens.remove(authToken);
    }
}
