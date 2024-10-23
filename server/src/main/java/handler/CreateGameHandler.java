package handler;

import request.CreateGameRequest;
import service.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler extends Handler {

    public CreateGameHandler() {
    }

    public static String doHandle(Request req, Response res){
        String authToken = ""; String gameName = "";
        CreateGameRequest gameReq = new CreateGameRequest(authToken, gameName);
        CreateGameService gameSer = new CreateGameService();
        int gameID = gameSer.createGame(gameReq);
        return "{gameID: " + gameID + "}";
    }
}
