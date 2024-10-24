package handler;

import model.AuthData;
import request.LoginRequest;
import service.UserService;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

public class LoginHandler extends Handler {

    public LoginHandler() {
    }

    public static String doHandle(Request req, Response res){
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
}
