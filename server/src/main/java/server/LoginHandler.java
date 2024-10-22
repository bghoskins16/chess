package server;

public class LoginHandler extends Handler {

    public LoginHandler() {
    }

    public AuthData doHandle(String username, String password){
        //create a login request
        LoginRequest loginReq = new LoginRequest(username, password);

        // Run the login service using the login request and return response
       LoginService loginSer = new LoginService();
       return loginSer.login(loginReq);
    }
}
