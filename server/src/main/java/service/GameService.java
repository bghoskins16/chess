package service;

import dataaccess.DataAccess;
import model.AuthData;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;

import java.util.Collection;

public class GameService extends Services {

    public GameService(DataAccess dataAccess) {
        super(dataAccess);
    }

    public int createGame(CreateGameRequest gameReq){
        AuthData auth = database.getAuth(gameReq.authToken());
        if (auth != null){
            return database.createGame(gameReq.gameName());
        }
        return -1;
    }

    public Collection<GameData> listGames(ListGamesRequest listReq){
        AuthData auth = database.getAuth(listReq.authToken());
        if (auth != null) {
            return database.listGames();
        }
        return null;
    }

    public void joinGame(JoinGameRequest joinReq){
        AuthData auth = database.getAuth(joinReq.authToken());
        if (auth != null) {
            GameData game = database.getGame(joinReq.gameID());
            if (game != null){
                // Check color is valid and that the color is open
                //database.editGame ..
            }
        }
    }
}
