package service;

import dataaccess.DataAccess;
import model.AuthData;
import model.UserData;
import request.LoginRequest;
import request.LogoutRequest;
import request.RegisterRequest;

public class UserService extends Services {

    public UserService(DataAccess dataAccess) {
        super(dataAccess);
    }

    public AuthData register(RegisterRequest r) throws ServiceException{
        if (database.getUser(r.username()) == null){
            database.createUser(new UserData(r.username(), r.password(), r.email()));
            return database.createAuth(r.username());
        }
        else{
            System.out.println("reregister");
            throw new ServiceException("Error: already taken");
        }
    }

    public AuthData login(LoginRequest loginReq){
        UserData data = database.getUser(loginReq.username());
        return database.createAuth(loginReq.username());
    }

    public void logout(LogoutRequest logoutReq){
        AuthData auth = database.getAuth(logoutReq.authToken());
        if (auth != null){
            database.deleteAuth(auth);
        }
    }
}
