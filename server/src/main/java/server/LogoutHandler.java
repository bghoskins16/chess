package server;

public class LogoutHandler extends Handler{

    public LogoutHandler() {
    }

    public String doHandle(String authToken){
        LogoutRequest logoutReq = new LogoutRequest(authToken);

        LogoutService logoutSer = new LogoutService();

        logoutSer.logout(logoutReq);

        return "{}";
    }
}
