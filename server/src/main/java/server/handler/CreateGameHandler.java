package server.handler;

import server.request.CreateGameRequest;
import server.service.CreateGameService;

public class CreateGameHandler extends Handler {

    public CreateGameHandler() {
    }

    public String doHandle(String authToken, String gameName){
        CreateGameRequest gameReq = new CreateGameRequest(authToken, gameName);
        CreateGameService gameSer = new CreateGameService();
        int gameID = gameSer.createGame(gameReq);
        return "{gameID: " + gameID + "}";
    }
}
