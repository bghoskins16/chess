package handler;

import spark.Request;
import spark.Response;
import service.Service;

public class ClearHandler extends Handler {

    public ClearHandler() {
    }

    public static String doHandle(Request req, Response res){
        service.Service clearSer = new Service();
        clearSer.clear();
        return "{}";
    }
}
