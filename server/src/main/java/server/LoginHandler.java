package server;

public class LoginHandler extends Handler {

    public LoginHandler() {
    }

    void doHandle(String username, String password){
        //create a login request
        LoginRequest loginReq = new LoginRequest(username, password);

        // Run the login service using the login request
       LoginService loginSer = new LoginService();
       loginSer.login(loginReq);

        // return json
    }
}
