package service;

import dataaccess.*;
import model.AuthData;
import server.ResponseException;

public class Service {
    static UserDataAccess userDatabase;// = new UserMemoryDataAccess();
    static AuthDataAccess authDatabase;// = new AuthMemoryDataAccess();
    static GameDataAccess gameDatabase;// = new GameMemoryDataAccess();

    public Service(UserDataAccess userDatabase, AuthDataAccess authDatabase, GameDataAccess gameDatabase) {
        Service.authDatabase = authDatabase;
        Service.gameDatabase = gameDatabase;
        Service.userDatabase = userDatabase;
    }

    public Service(){
        try {
            Service.authDatabase = new AuthMySqlDataAccess();
            Service.gameDatabase = new GameMySqlDataAccess();
            Service.userDatabase = new UserMySqlDataAccess();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        userDatabase.clear();
        authDatabase.clear();
        gameDatabase.clear();
    }

    AuthData authenticate(String authToken) throws ResponseException {
        AuthData auth = authDatabase.getAuth(authToken);
        if (auth == null) {
            throw new ResponseException(401, "Error: unauthorized");
        }
        return auth;
    }

}
