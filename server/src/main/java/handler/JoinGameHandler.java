package handler;

import request.JoinGameRequest;
import service.JoinGameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler extends Handler {

    public JoinGameHandler() {
    }

    public static String doHandle(Request req, Response res){
        String authToken=""; String color=""; int gameID = 0;
        JoinGameRequest joinReq = new JoinGameRequest(authToken, color, gameID);
        JoinGameService joinSer = new JoinGameService();
        joinSer.joinGame(joinReq);
        return "{}";
    }
}
