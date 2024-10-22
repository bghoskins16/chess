package server;

import model.AuthData;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;
import model.UserData;

public class LoginService extends Services{

    public LoginService() {
    }

    public AuthData login(LoginRequest loginReq){
        UserData data = database.getUser(loginReq.username());
        return database.createAuth(loginReq.username());
    }
}
