package server;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;

public class LoginService extends Services{
    DataAccess database;

    public LoginService() {
        database = new MemoryDataAccess();
    }

    public LoginResult login(LoginRequest loginReq){
        UserData data = database.getUser(loginReq.username());
        LoginResult result = database.createAuth(loginReq.username());
        return result;
    }
}
