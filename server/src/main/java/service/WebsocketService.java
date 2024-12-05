package service;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import model.GameData;
import server.ResponseException;

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

    public GameData makeMove(Integer gameID, ChessMove move) throws ResponseException {
        GameData oldData = getGame(gameID);
        ChessGame game = oldData.game();
        try {
            if (game.isGameOver()){
                throw new ResponseException(0, "Error: Game is over");
            }
            game.makeMove(move);
            gameDatabase.updateGame(gameID,game);
            return new GameData(oldData.gameID(), oldData.whiteUsername(), oldData.blackUsername(), oldData.gameName(), game);
        } catch (InvalidMoveException e) {
            throw new ResponseException(0, "Error: Invalid Move");
        }
    }

    public void endGame(Integer gameID) throws ResponseException {
        GameData oldData = getGame(gameID);
        ChessGame game = oldData.game();
        game.setGameOver(true);
        gameDatabase.updateGame(gameID,game);
    }
}
