package server.service;

public class ClearService extends Services {

    public ClearService() {
    }

    public void clear(){
        database.clear();
    }
}
