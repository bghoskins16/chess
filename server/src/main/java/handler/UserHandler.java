package handler;

import model.AuthData;
import request.LoginRequest;
import request.LogoutRequest;
import request.RegisterRequest;
import response.ErrorResponse;
import server.ResponseException;
import service.UserService;
import spark.Request;
import spark.Response;

public class UserHandler extends Handler {

    public UserHandler() {
    }

    public static String registerHandler(Request req, Response res) {
        RegisterRequest registerReq = serializer.fromJson(req.body(), RegisterRequest.class);

        UserService registerSer = new UserService();
        try {
            AuthData auth = registerSer.register(registerReq);
            return serializer.toJson(auth);

        } catch (ResponseException ex) {
            res.status(ex.statusCode());
            return serializer.toJson(new ErrorResponse(ex.getMessage()));
        }
    }

    public static String loginHandler(Request req, Response res) {
        LoginRequest loginReq = serializer.fromJson(req.body(), LoginRequest.class);
        UserService loginSer = new UserService();

        try {
            AuthData auth = loginSer.login(loginReq);
            return serializer.toJson(auth);
        } catch (ResponseException ex) {
            res.status(ex.statusCode());
            return serializer.toJson(new ErrorResponse(ex.getMessage()));
        }
    }

    public static String logoutHandler(Request req, Response res) {
        LogoutRequest logoutReq = new LogoutRequest(req.headers("authorization"));
        UserService logoutSer = new UserService();

        try {
            logoutSer.logout(logoutReq);
        } catch (ResponseException ex) {
            res.status(ex.statusCode());
            return serializer.toJson(new ErrorResponse(ex.getMessage()));
        }

        return "{}";
    }
}
