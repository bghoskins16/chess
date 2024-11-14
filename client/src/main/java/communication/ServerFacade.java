package communication;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import request.*;
import response.ListResponse;

import java.util.ArrayList;
import java.util.Collection;

public class ServerFacade {

    ClientCommunicator com = new ClientCommunicator();
    Collection<GameData> currGameList = new ArrayList<>();

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

        if (response.startsWith("Error")) {
            System.out.println(response);
            return null;
        }

        System.out.println("Successfully registered " + username);
        return response;
    }

    public String login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest(username, password);
        String response = com.login(loginRequest);

        if (response.startsWith("Error")) {
            System.out.println(response);
            return null;
        }

        System.out.println("Successfully logged in " + username);
        return response;
    }

    public boolean logout(String authToken) {
        LogoutRequest logoutRequest = new LogoutRequest(authToken);
        String response = com.logout(logoutRequest);

        if (response.startsWith("Error")) {
            System.out.println(response);
            return false;
        }

        System.out.println("Successfully Logged Out");
        return true;
    }

    public void listGames(String authToken) {
        ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);
        String response = com.listGames(listGamesRequest);
        currGameList = new Gson().fromJson(response, ListResponse.class).games();

        int i = 1;
        for (GameData game : currGameList) {
            System.out.println(i + ":" +
                    " name -> " + game.gameName() +
                    " white -> " + game.whiteUsername() +
                    " black -> " + game.blackUsername());
            i++;
        }


    }

    public void createGame(String authToken, String name) {
        CreateGameRequest createGameRequest = new CreateGameRequest(authToken, name);
        String response = com.createGame(createGameRequest);

        if (response.startsWith("Error")) {
            System.out.println(response);
        }

        System.out.println(name + " has been created! List games to view the id.");
    }

    public boolean joinGame(String authToken, String id, String color) {
        //verify id and color
        int idInt = Integer.parseInt(id); // Need to verify it is an int first
        if (idInt < 1 || idInt > currGameList.size()) {
            System.out.println("Please enter a valid game id (List games to view ids)");
            return false;
        }

        ChessGame.TeamColor teamColor = null;
        if (color.equals("white")) {
            teamColor = ChessGame.TeamColor.WHITE;
        } else if (color.equals("black")) {
            teamColor = ChessGame.TeamColor.BLACK;
        } else {
            System.out.println("Please select either 'white' or 'black'");
            return false;
        }

        JoinGameRequest joinGameRequest = new JoinGameRequest(authToken, teamColor, idInt);
        String response = com.joinGame(joinGameRequest);

        if (response.startsWith("Error")) {
            System.out.println(response);
            return false;
        }

        return true;
    }
}
