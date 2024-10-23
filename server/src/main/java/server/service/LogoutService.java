package server.service;

import model.AuthData;
import server.request.LogoutRequest;

public class LogoutService extends Services {

    public LogoutService() {
    }

    public boolean logout(LogoutRequest logoutReq){
        AuthData auth = database.getAuth(logoutReq.authToken());
        if (auth != null){
            database.deleteAuth(auth);
            return true;
        }
        return false;
    }
}