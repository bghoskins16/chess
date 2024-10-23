package server.service;

import model.AuthData;

import model.UserData;
import server.request.LoginRequest;

public class LoginService extends Services {

    public LoginService() {
    }

    public AuthData login(LoginRequest loginReq){
        UserData data = database.getUser(loginReq.username());
        return database.createAuth(loginReq.username());
    }
}
