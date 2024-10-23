package server;

import model.AuthData;

public class CreateGameService extends Services{

    public CreateGameService() {
    }

    public int createGame(CreateGameRequest gameReq){
        AuthData auth = database.getAuth(gameReq.authToken());
        if (auth != null){
            return database.createGame(gameReq.gameName());
        }
        return -1;
    }
}
