package server.handler;

import server.request.JoinGameRequest;
import server.service.JoinGameService;

public class JoinGameHandler extends Handler {

    public JoinGameHandler() {
    }

    public String doHandle(String authToken, String color, int gameID){
        JoinGameRequest joinReq = new JoinGameRequest(authToken, color, gameID);
        JoinGameService joinSer = new JoinGameService();
        joinSer.joinGame(joinReq);
        return "{}";
    }
}