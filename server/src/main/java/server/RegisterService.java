package server;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;
import model.AuthData;
import model.UserData;

public class RegisterService extends Services{

    public RegisterService() {
    }

    public AuthData register(RegisterRequest RegisterReq){
        if (database.getUser(RegisterReq.username()) == null){
            database.createUser(new UserData(RegisterReq.username(), RegisterReq.password(), RegisterReq.email()));
            return database.createAuth(RegisterReq.username());
        }
        return null;
    }
}
