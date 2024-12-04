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

    public GameData connect(Integer gameID) throws ResponseException {
        GameData game = gameDatabase.getGame(gameID);
        if (game == null) {
            throw new ResponseException(400, "Error: problem retrieving game");
        }
        return game;
    }
}
