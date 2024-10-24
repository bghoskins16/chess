package service;

import dataaccess.*;

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

}
