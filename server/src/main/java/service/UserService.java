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

    public AuthData register(RegisterRequest r) throws ResponseException {
        if (r.username() == null || r.password() == null || r.email() == null){
            throw new ResponseException(400, "Error: bad request");
        }

        if (userDatabase.getUser(r.username()) == null){
            userDatabase.createUser(new UserData(r.username(), r.password(), r.email()));
            return authDatabase.createAuth(r.username());
        }
        else{
            throw new ResponseException(403, "Error: already taken");
        }
    }

    public AuthData login(LoginRequest loginReq){
        UserData data = userDatabase.getUser(loginReq.username());
        return authDatabase.createAuth(loginReq.username());
    }

    public void logout(LogoutRequest logoutReq){
        AuthData auth = authDatabase.getAuth(logoutReq.authToken());
        if (auth != null){
            authDatabase.deleteAuth(auth);
        }
    }
}
