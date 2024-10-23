package handler;

import model.GameData;
import request.ListGamesRequest;
import service.ListGamesService;
import spark.Request;
import spark.Response;
import spark.*;

import java.util.Collection;

public class ListGamesHandler extends Handler {

    public ListGamesHandler() {
    }

    public static String doHandle(Request req, Response res){
        String authToken = "";
        ListGamesRequest listReq = new ListGamesRequest(authToken);
        ListGamesService listSer = new ListGamesService();
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
