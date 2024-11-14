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
        System.out.println("Successfully logged in " + username);
        return "auth";
    }

    public boolean logout() {
        return true;
    }

    public void listGames() {

    }

    public void createGame(String name) {

    }

    public void joinGame(String id, String color) {
        //convert id to an int
    }
}
