package handler;

import model.AuthData;
import request.LoginRequest;
import service.LoginService;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

public class LoginHandler extends Handler {

    public LoginHandler() {
    }

    public static String doHandle(Request req, Response res){
        LoginRequest loginRequest = new Gson().fromJson(req.body(), LoginRequest.class);

        //create a login request
        LoginRequest loginReq = new LoginRequest(loginRequest.username(), loginRequest.password());

        // Run the login service using the login request
       LoginService loginSer = new LoginService();
       AuthData auth = loginSer.login(loginReq);

        // return json
        return "{\"username\": \"" + auth.username() + "\", \"authToken\": \"" + auth.authToken() + "\"}";
    }
}
