package handler;

import request.LogoutRequest;
import service.LogoutService;
import spark.Request;
import spark.Response;
import spark.*;

public class LogoutHandler extends Handler {

    public LogoutHandler() {
    }

    public static String doHandle(Request req, Response res){
        String authToken = "";
        LogoutRequest logoutReq = new LogoutRequest(authToken);

        LogoutService logoutSer = new LogoutService();

        logoutSer.logout(logoutReq);

        return "{}";
    }
}
