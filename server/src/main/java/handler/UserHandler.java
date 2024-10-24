package handler;

import com.google.gson.Gson;
import model.AuthData;
import request.LoginRequest;
import request.LogoutRequest;
import request.RegisterRequest;
import response.ErrorResponse;
import service.UserService;
import spark.Request;
import spark.Response;

public class UserHandler {

    public UserHandler() {
    }

    public static String loginHandler(Request req, Response res){
        var serializer = new Gson();
        LoginRequest loginRequest = serializer.fromJson(req.body(), LoginRequest.class);

        //create a login request
        LoginRequest loginReq = new LoginRequest(loginRequest.username(), loginRequest.password());

        // Run the login service using the login request
        UserService loginSer = new UserService();
        AuthData auth = loginSer.login(loginReq);

        // return json
        //return "{\"username\": \"" + auth.username() + "\", \"authToken\": \"" + auth.authToken() + "\"}";
        return serializer.toJson(auth);
    }

    public static String logoutHandler(Request req, Response res){
        String authToken = "";
        LogoutRequest logoutReq = new LogoutRequest(authToken);

        UserService logoutSer = new UserService();

        logoutSer.logout(logoutReq);

        return "{}";
    }

    public static String registerHandler(Request req, Response res){
        var serializer = new Gson();
        RegisterRequest registerRequest = serializer.fromJson(req.body(), RegisterRequest.class);

        //create a register request
        RegisterRequest RegisterReq = new RegisterRequest(registerRequest.username(), registerRequest.password(), registerRequest.email());

        // Run the register service using the register request
        UserService RegisterSer = new UserService();
        try {
            AuthData auth = RegisterSer.register(RegisterReq);
            // return json
            return serializer.toJson(auth);

        }catch (Exception e){
            res.status(403);
            return serializer.toJson(new ErrorResponse("Error: already taken"));
        }
    }
}
