package server;

import model.AuthData;

public class LoginHandler extends Handler {

    public LoginHandler() {
    }

    public String doHandle(String username, String password){
        //create a login request
        LoginRequest loginReq = new LoginRequest(username, password);

        // Run the login service using the login request
       LoginService loginSer = new LoginService();
       AuthData auth = loginSer.login(loginReq);

        // return json
        return "{username: " + auth.username() + ", authToken: " + auth.authToken() + "}";
    }
}
