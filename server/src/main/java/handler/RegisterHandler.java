package handler;

import model.AuthData;
import request.LoginRequest;
import request.RegisterRequest;
import response.ErrorResponse;
import service.UserService;
import service.UserService;
import spark.*;
import com.google.gson.Gson;
import java.io.IOException;

public class RegisterHandler extends Handler {

    public RegisterHandler() {
    }

    public static String doHandle(Request req, Response res){
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
