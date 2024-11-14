package communication;

import request.*;

public class ServerFacade {

    ClientCommunicator com = new ClientCommunicator();

    public ServerFacade() {
    }

    public void clear() {
        String response = com.clear();
        if (response.startsWith("Error")) {
            System.out.println(response);
        }
    }

    public String register(String username, String password, String email) {
        RegisterRequest registerRequest = new RegisterRequest(username, password, email);
        String response = com.register(registerRequest);

        if (response.startsWith("Error")){
            System.out.println(response);
            return null;
        }

        System.out.println("Successfully registered " + username);
        return response;
    }

    public String login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest(username, password);
        String response = com.login(loginRequest);

        if (response.startsWith("Error")){
            System.out.println(response);
            return null;
        }

        System.out.println("Successfully logged in " + username);
        return response;
    }

    public boolean logout(String authToken) {
        LogoutRequest logoutRequest = new LogoutRequest(authToken);
        String response = com.logout(logoutRequest);

        if (response.startsWith("Error")){
            System.out.println(response);
            return false;
        }

        System.out.println("Successfully Logged Out");
        return true;
    }

    public void listGames(String authToken) {

    }

    public void createGame(String authToken, String name) {

    }

    public void joinGame(String authToken, String id, String color) {
        //convert id to an int
    }
}
