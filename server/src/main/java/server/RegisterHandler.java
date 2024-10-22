package server;

import model.AuthData;

public class RegisterHandler extends Handler{

    public RegisterHandler() {
    }

    public String doHandle(String username, String password, String email){
        //create a register request
        RegisterRequest RegisterReq = new RegisterRequest(username, password, email);

        // Run the register service using the register request
        RegisterService RegisterSer = new RegisterService();
        AuthData auth = RegisterSer.register(RegisterReq);

        // return json
        return "{username: " + auth.username() + ", authToken: " + auth.authToken() + "}";
    }
}
