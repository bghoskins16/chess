package service;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
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

    public GameData getGame(Integer gameID) throws ResponseException {
        GameData game = gameDatabase.getGame(gameID);
        if (game == null) {
            throw new ResponseException(400, "Error: problem retrieving game");
        }
        return game;
    }

    public GameData mokeMove(Integer gameID, ChessMove move) throws ResponseException {
        GameData oldData = getGame(gameID);
        ChessGame game = oldData.game();
        try {
            game.makeMove(move);
            return new GameData(oldData.gameID(), oldData.whiteUsername(), oldData.blackUsername(), oldData.gameName(), game);
        } catch (InvalidMoveException e) {
            throw new ResponseException(0, "Error: Invalid Move");
        }
    }
}
