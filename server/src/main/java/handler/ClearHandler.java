package handler;

import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler extends Handler {

    public ClearHandler() {
    }

    public static String doHandle(Request req, Response res){
        ClearService clearSer = new ClearService();
        clearSer.clear();
        return "{}";
    }
}
