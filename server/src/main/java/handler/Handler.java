package handler;

import com.google.gson.Gson;
import server.ResponseException;
import service.Service;
import spark.*;

public class Handler {
    static Gson serializer = new Gson();

    public Handler() {
    }

    public static String clearHandler(Request req, Response res) {
        service.Service clearSer = new Service();
        clearSer.clear();
        return "{}";
    }

    public static void exceptionHandler(ResponseException ex, Request req, Response res) {
        res.status(ex.statusCode());
    }
}
