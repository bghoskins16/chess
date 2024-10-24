package service;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;
import server.ResponseException;

import java.util.Collection;

public class GameService extends Service {

    public GameService() {
    }

    public int createGame(CreateGameRequest gameReq) throws ResponseException {
        authenticate(gameReq.authToken());
        if (gameReq.gameName() == null){
            throw new ResponseException(400, "Error: bad request");
        }
        return gameDatabase.createGame(gameReq.gameName());
    }

    public Collection<GameData> listGames(ListGamesRequest listReq) throws ResponseException{
        authenticate(listReq.authToken());
        return gameDatabase.listGames();
    }

    public void joinGame(JoinGameRequest joinReq) throws ResponseException{
        AuthData auth = authenticate(joinReq.authToken());
        GameData game = gameDatabase.getGame(joinReq.gameID());
        GameData newGame;
        if (game == null) {
            throw new ResponseException(400, "Error: unauthorized");
        }
        if (joinReq.playerColor() == ChessGame.TeamColor.WHITE){
            if (game.whiteUsername() != null){
                throw new ResponseException(403, "Error: already taken");
            }
            newGame = new GameData(game.gameID(), auth.username(), game.blackUsername(), game.gameName(), game.game());
        }
        else if (joinReq.playerColor() == ChessGame.TeamColor.BLACK){
            if (game.blackUsername() != null){
                throw new ResponseException(403, "Error: already taken");
            }
            newGame = new GameData(game.gameID(), game.whiteUsername(), auth.username(), game.gameName(), game.game());
        }
        else {
            throw new ResponseException(400, "Error: bad request" );
        }

        gameDatabase.updateGame(game, newGame);
    }
}
