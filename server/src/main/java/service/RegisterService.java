package service;

import model.AuthData;
import model.UserData;
import request.RegisterRequest;

public class RegisterService extends Services {

    public RegisterService() {
    }

    public AuthData register(RegisterRequest r){
        if (database.getUser(r.username()) == null){
            database.createUser(new UserData(r.username(), r.password(), r.email()));
            return database.createAuth(r.username());
        }
        return null;
    }
}
