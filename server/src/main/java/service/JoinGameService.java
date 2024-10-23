package service;

import model.AuthData;
import model.GameData;
import request.JoinGameRequest;

public class JoinGameService extends Services {

    public JoinGameService() {
    }

    public boolean joinGame(JoinGameRequest joinReq){
        AuthData auth = database.getAuth(joinReq.authToken());
        if (auth != null) {
            GameData game = database.getGame(joinReq.gameID());
            if (game != null){
                // Check color is valid and that the color is open
                //database.editGame ..
                return true;
            }
        }
        return false;
    }
}
