package handler;

import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;
import request.LoginRequest;
import response.CreateGameResponse;
import response.ErrorResponse;
import response.ListResponse;
import server.ResponseException;
import service.GameService;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class GameHandler extends Handler {

    public GameHandler() {
    }

    public static String createGameHandler(Request req, Response res) {
        CreateGameRequest gameReq = serializer.fromJson(req.body(), CreateGameRequest.class);
        gameReq = new CreateGameRequest(req.headers("authorization"), gameReq.gameName());
        GameService gameSer = new GameService();
        try {
            int gameID = gameSer.createGame(gameReq);
            return serializer.toJson(new CreateGameResponse(gameID));
        } catch (ResponseException ex) {
            res.status(ex.StatusCode());
            return serializer.toJson(new ErrorResponse(ex.getMessage()));
        }
    }

    public static String joinGameHandler(Request req, Response res) {
        JoinGameRequest joinReq = serializer.fromJson(req.body(), JoinGameRequest.class);
        joinReq = new JoinGameRequest(req.headers("authorization"), joinReq.playerColor(), joinReq.gameID());
        GameService joinSer = new GameService();
        try {
            joinSer.joinGame(joinReq);
            return "{}";
        } catch (ResponseException ex) {
            res.status(ex.StatusCode());
            return serializer.toJson(new ErrorResponse(ex.getMessage()));
        }
    }

    public static String listGamesHandler(Request req, Response res) {
        ListGamesRequest listReq = new ListGamesRequest(req.headers("authorization"));
        GameService listSer = new GameService();
        try {
            Collection<GameData> games = listSer.listGames(listReq);
            return serializer.toJson(new ListResponse(games));

        } catch (ResponseException ex) {
            res.status(ex.StatusCode());
            return serializer.toJson(new ErrorResponse(ex.getMessage()));
        }
    }
}
