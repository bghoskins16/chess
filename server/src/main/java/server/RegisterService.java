package server;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;
import model.AuthData;
import model.UserData;

public class RegisterService extends Services{

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
