package service;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;
import server.ResponseException;

import java.util.Collection;

public class WebsocketService extends Service {

    public WebsocketService() {
    }

    public String connect(String authToken, Integer gameID) throws ResponseException {

        AuthData auth = authenticate(authToken);
        GameData game = gameDatabase.getGame(gameID);
        if (game == null) {
            throw new ResponseException(400, "Error: unauthorized");
        }
        return auth.username();
    }
}
