package service;

import model.AuthData;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;

import java.util.Collection;

public class GameService extends Service {

    public GameService() {
    }

    public int createGame(CreateGameRequest gameReq){
        AuthData auth = authDatabase.getAuth(gameReq.authToken());
        if (auth != null){
            return gameDatabase.createGame(gameReq.gameName());
        }
        return -1;
    }

    public Collection<GameData> listGames(ListGamesRequest listReq){
        AuthData auth = authDatabase.getAuth(listReq.authToken());
        if (auth != null) {
            return gameDatabase.listGames();
        }
        return null;
    }

    public void joinGame(JoinGameRequest joinReq){
        AuthData auth = authDatabase.getAuth(joinReq.authToken());
        if (auth != null) {
            GameData game = gameDatabase.getGame(joinReq.gameID());
            if (game != null){
                // Check color is valid and that the color is open
                //database.editGame ..
            }
        }
    }
}
