package handler;

import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;
import service.GameService;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class GameHandler extends Handler {

    public GameHandler() {
    }

    public static String createGameHandler(Request req, Response res){
        String authToken = ""; String gameName = "";
        CreateGameRequest gameReq = new CreateGameRequest(authToken, gameName);
        GameService gameSer = new GameService();
        int gameID = gameSer.createGame(gameReq);
        return "{gameID: " + gameID + "}";
    }

    public static String joinGameHandler(Request req, Response res){
        String authToken=""; String color=""; int gameID = 0;
        JoinGameRequest joinReq = new JoinGameRequest(authToken, color, gameID);
        GameService joinSer = new GameService();
        joinSer.joinGame(joinReq);
        return "{}";
    }

    public static String listGamesHandler(Request req, Response res){
        String authToken = "";
        ListGamesRequest listReq = new ListGamesRequest(authToken);
        GameService listSer = new GameService();
        Collection<GameData> games = listSer.listGames(listReq);
        StringBuilder retStr  = new StringBuilder("{");
        boolean addComma = false;
        for (GameData game: games){
            if (addComma) {
                retStr.append(", ");
            }
            addComma = true;

            retStr.append(game.toString());
        }
        retStr.append("}");      //add delete char to this
        return retStr.toString();
    }
}
