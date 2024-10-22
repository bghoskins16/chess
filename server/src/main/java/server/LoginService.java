package server;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;

public class LoginService extends Services{
    DataAccess database;

    public LoginService() {
        database = new MemoryDataAccess();
    }

    public void login(LoginRequest loginReq){
        UserData data = database.getUser(loginReq.username());
    }
}
