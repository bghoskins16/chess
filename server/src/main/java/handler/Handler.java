package handler;

import service.Service;
import spark.*;

public class Handler {

    public Handler() {
    }

    public static String clearHandler(Request req, Response res){
        service.Service clearSer = new Service();
        clearSer.clear();
        return "{}";
    }
}
