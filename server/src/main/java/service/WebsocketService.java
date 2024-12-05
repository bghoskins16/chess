package service;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import model.AuthData;
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

    public GameData makeMove(Integer gameID, AuthData auth ,ChessMove move) throws ResponseException {
        GameData oldData = getGame(gameID);
        ChessGame game = oldData.game();
        try {
            if (game.isGameOver()) {
                throw new ResponseException(0, "Error: Game is over");
            }


            if (( game.getTeamTurn() == ChessGame.TeamColor.WHITE && !auth.username().equals(oldData.whiteUsername()) ) ||
                    ( game.getTeamTurn() == ChessGame.TeamColor.BLACK && !auth.username().equals(oldData.blackUsername())) ){
                throw new ResponseException(0, "Error: You can't move that piece");
            }

            game.makeMove(move);
            gameDatabase.updateGame(gameID, game);
            return new GameData(oldData.gameID(), oldData.whiteUsername(), oldData.blackUsername(), oldData.gameName(), game);
        } catch (InvalidMoveException e) {
            throw new ResponseException(0, "Error: Invalid Move");
        }
    }

    public void endGame(Integer gameID) throws ResponseException {
        GameData oldData = getGame(gameID);
        ChessGame game = oldData.game();

        if (game.isGameOver()) {
            throw new ResponseException(0, "Error: Game is over");
        }

        game.setGameOver(true);
        gameDatabase.updateGame(gameID, game);
    }

    public void leaveGame(Integer gameID, String username) throws ResponseException{
        GameData game = getGame(gameID);
        if (username.equals(game.whiteUsername())){
            gameDatabase.addUserWhite(gameID, null);
        }
        else if (username.equals(game.blackUsername())){
            gameDatabase.addUserBlack(gameID, null);
        }
    }

    public boolean isObserver(Integer gameID, String username) throws ResponseException{
        GameData game = getGame(gameID);
        return (!username.equals(game.whiteUsername()) && !username.equals(game.blackUsername()));
    }
}
