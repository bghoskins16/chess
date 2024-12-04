package communication;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import request.*;
import response.ListResponse;
import ui.DrawChessBoard;
import websocket.messages.LoadGameMessage;
import websocket.messages.ServerMessage;

import java.util.ArrayList;
import java.util.Collection;

public class ServerFacade {
    int defaultPort = 8080;
    boolean isConnectedWhite = false;
    WebsocketCommunicator ws = null;
    HTTPCommunicator com;
    Collection<GameData> currGameList = new ArrayList<>();

    public ServerFacade() {
        com = new HTTPCommunicator(defaultPort);
    }

    public ServerFacade(int port) {
        com = new HTTPCommunicator(port);
    }

    ServerMessageObserver serverMessageObserver = new ServerMessageObserver() {
        @Override
        public void notify(ServerMessage message) {
            if (message.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME){
                LoadGameMessage m = (LoadGameMessage) message;

                DrawChessBoard drawChessBoard = new DrawChessBoard();
                drawChessBoard.setBoard(m.getGame().getBoard());
                drawChessBoard.drawBoard(isConnectedWhite);

            }
        }
    };

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

    public String listGames(String authToken) {
        ListGamesRequest listGamesRequest = new ListGamesRequest(authToken);
        String response = com.listGames(listGamesRequest);
        currGameList = new Gson().fromJson(response, ListResponse.class).games();

        StringBuilder output = new StringBuilder();
        int i = 1;
        for (GameData game : currGameList) {
            output.append("  id -> ").append(i).append(":");
            output.append("   Game Name -> ").append(game.gameName());
            output.append("   White Player -> ").append((game.whiteUsername() == null) ? "'AVAILABLE'" : game.whiteUsername());
            output.append("   Black Player -> ").append((game.blackUsername() == null) ? "'AVAILABLE'" : game.blackUsername());
            output.append("\n");
            i++;
        }

        System.out.print(output);
        return output.toString();

    }

    public boolean createGame(String authToken, String name) {
        CreateGameRequest createGameRequest = new CreateGameRequest(authToken, name);
        String response = com.createGame(createGameRequest);

        if (response.startsWith("Error")) {
            System.out.println(response);
            return false;
        }

        System.out.println(name + " has been created! List games to view the id.");
        return true;
    }

    public boolean joinGame(String authToken, String id, String color) {
        //verify id and color
        int idInt;
        try {
            idInt = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            idInt = -1;
        }
        if (idInt < 1 || idInt > currGameList.size()) {
            System.out.println("Please enter a valid game id (List games to view ids)");
            return false;
        }

        ChessGame.TeamColor teamColor = null;
        if (color.equals("white")) {
            teamColor = ChessGame.TeamColor.WHITE;
            isConnectedWhite = true;
        } else if (color.equals("black")) {
            teamColor = ChessGame.TeamColor.BLACK;
            isConnectedWhite = false;
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

        //Add websocket CONNECT here
        try {
            ws = new WebsocketCommunicator(serverMessageObserver);
            ws.connect(idInt, authToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
