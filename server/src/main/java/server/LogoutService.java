package server;

import model.AuthData;
import model.UserData;

public class LogoutService extends Services{

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
