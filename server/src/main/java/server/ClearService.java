package server;

import model.AuthData;

public class ClearService extends Services{

    public ClearService() {
    }

    public void clear(){
        database.clear();
    }
}
