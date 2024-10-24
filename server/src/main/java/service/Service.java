package service;

import dataaccess.*;
import model.AuthData;
import server.ResponseException;

public class Service {
    static final UserDataAccess userDatabase = new UserMemoryDataAccess();
    static final AuthDataAccess authDatabase = new AuthMemoryDataAccess();
    static final GameDataAccess gameDatabase = new GameMemoryDataAccess();

    public Service() {
    }

    public void clear(){
        userDatabase.clear();
        authDatabase.clear();
        gameDatabase.clear();
    }

    AuthData authenticate(String authToken) throws ResponseException {
        AuthData auth = authDatabase.getAuth(authToken);
        if (auth == null){
            throw new ResponseException(401, "Error: unauthorized");
        }
        return auth;
    }

}
