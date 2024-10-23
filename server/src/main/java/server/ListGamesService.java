package server;

import chess.ChessGame;
import model.AuthData;
import model.GameData;

import java.util.Collection;

public class ListGamesService extends Services{

    public ListGamesService() {
    }

    public Collection<GameData> listGames(ListGamesRequest listReq){
        AuthData auth = database.getAuth(listReq.authToken());
        if (auth != null) {
            return database.listGames();
        }
        return null;
    }
}
