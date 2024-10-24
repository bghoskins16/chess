package service;

import model.AuthData;
import model.UserData;
import request.LoginRequest;
import request.LogoutRequest;
import request.RegisterRequest;
import server.ResponseException;

public class UserService extends Service {

    public UserService() {
    }

    public AuthData register(RegisterRequest registerReq) throws ResponseException {
        if (registerReq.username() == null || registerReq.password() == null || registerReq.email() == null){
            throw new ResponseException(400, "Error: bad request");
        }

        if (userDatabase.getUser(registerReq.username()) == null){
            userDatabase.createUser(new UserData(registerReq.username(), registerReq.password(), registerReq.email()));
            return authDatabase.createAuth(registerReq.username());
        }
        else{
            throw new ResponseException(403, "Error: already taken");
        }
    }

    public AuthData login(LoginRequest loginReq) throws ResponseException{
        if (loginReq.username() == null || loginReq.password() == null){
            throw new ResponseException(400, "Error: bad request");
        }

        UserData data = userDatabase.getUser(loginReq.username());

        if (data == null){
            throw new ResponseException(401, "Error: unauthorized");
        }

        if (!data.password().equals(loginReq.password())){
            throw new ResponseException(401, "Error: unauthorized");
        }
        return authDatabase.createAuth(loginReq.username());
    }

    public void logout(LogoutRequest logoutReq) throws ResponseException{
        AuthData auth = authenticate(logoutReq.authToken());
        authDatabase.deleteAuth(auth);
    }
}
