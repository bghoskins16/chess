package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class AuthMemoryDataAccess implements AuthDataAccess {
    static private Collection<AuthData> authTokens = new ArrayList<>();

    int nextGameID = 0;

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear() {
        authTokens.clear();
    }

    //createAuth: Create a new authorization.
    public AuthData createAuth(String username) {
        AuthData newAuth = new AuthData(username, UUID.randomUUID().toString());
        authTokens.add(newAuth);
        return newAuth;
    }

    //getAuth: Retrieve an authorization given an authToken.
    public AuthData getAuth(String authToken) {
        for (AuthData auth : authTokens) {
            if (auth.authToken().equals(authToken)) {
                return auth;
            }
        }
        return null;
    }

    //deleteAuth: Delete an authorization so that it is no longer valid.
    public void deleteAuth(AuthData authToken) {
        authTokens.remove(authToken);
    }

    public Collection<AuthData> getAuthTokens() {
        return authTokens;
    }
}
