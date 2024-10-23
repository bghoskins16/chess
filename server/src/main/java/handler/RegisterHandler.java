package handler;

import model.AuthData;
import request.RegisterRequest;
import service.RegisterService;
import spark.*;

public class RegisterHandler extends Handler {

    public RegisterHandler() {
    }

    public static String doHandle(Request req, Response res){
        String username= ""; String password = ""; String email = "";
        //create a register request
        RegisterRequest RegisterReq = new RegisterRequest(username, password, email);

        // Run the register service using the register request
        RegisterService RegisterSer = new RegisterService();
        AuthData auth = RegisterSer.register(RegisterReq);

        // return json
        return "{username: " + auth.username() + ", authToken: " + auth.authToken() + "}";
    }
}
