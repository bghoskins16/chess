package communication;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;
import model.GameData;
import request.*;
import response.ListResponse;
import ui.DrawChessBoard;
import websocket.messages.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ServerFacade {
    int defaultPort = 8080;
    Boolean isConnectedWhite = null;
    int connectedGameId = -1;
    ChessGame connectedGame;
    WebsocketCommunicator ws = null;
    HTTPCommunicator com;
    Collection<GameData> currGameList = new ArrayList<>();
    ServerMessageObserver serverMessageObserver = new ServerMessageObserver() {
        @Override
        public void notify(String message) {

            ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
            if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME) {
                LoadGameMessage m = new Gson().fromJson(message, LoadGameMessage.class);

                connectedGame = m.getGame();
                drawBoard();
            } else if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.ERROR) {
                ErrorMessage m = new Gson().fromJson(message, ErrorMessage.class);
                System.out.println(m.getErrorMessage());
            } else if (serverMessage.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION) {
                NotificationMessage m = new Gson().fromJson(message, NotificationMessage.class);
                System.out.println(m.getMessage());
            }
        }
    };

    public ServerFacade() {
        com = new HTTPCommunicator(defaultPort);
    }

    public ServerFacade(int port) {
        com = new HTTPCommunicator(port);
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

        //Add websocket CONNECT here
        try {
            ws = new WebsocketCommunicator(serverMessageObserver);
            ws.connect(idInt, authToken);
        } catch (Exception ex) {
            System.out.println("Error: Problem with connection, please try again");
        }

        // Save these values to use for websocket commands
        isConnectedWhite = color.equals("white");
        connectedGameId = idInt;

        return true;
    }

    public void resign(String authToken) {
        try {
            ws.resign(connectedGameId, authToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void leave(String authToken) {
        try {
            ws.leave(connectedGameId, authToken);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean makeMove(String authToken, ChessMove move) {
        try {
            // Just check if it is your turn to move
            if (isConnectedWhite == null) {
                System.out.println("Must join a game to make moves");
                return false;
            }

            ChessGame.TeamColor whoseTurn = connectedGame.getTeamTurn();
            if ((isConnectedWhite && whoseTurn != ChessGame.TeamColor.WHITE) ||
                    (!isConnectedWhite && whoseTurn != ChessGame.TeamColor.BLACK)) {
                System.out.println("Please wait for your turn");
                return false;
            }

            ws.makeMove(connectedGameId, authToken, move);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public boolean observe(String authToken, String id) {
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

        try {
            ws = new WebsocketCommunicator(serverMessageObserver);
            ws.connect(idInt, authToken);
        } catch (Exception ex) {
            System.out.println("Error: Problem with connection, please try again");
            return false;
        }

        isConnectedWhite = null;
        connectedGameId = idInt;
        return true;
    }


    public void drawBoard() {
        DrawChessBoard drawChessBoard = new DrawChessBoard();
        drawChessBoard.setBoard(connectedGame.getBoard());
        drawChessBoard.drawBoard(Objects.requireNonNullElse(isConnectedWhite, true));
    }

    public void drawBoardWithMoves(ChessPosition position) {
        if (connectedGame.getBoard().getPiece(position) == null) {
            System.out.println("No piece at that location");
            return;
        }
        DrawChessBoard drawChessBoard = new DrawChessBoard();
        drawChessBoard.setGame(connectedGame);
        drawChessBoard.setHighlightPiece(position);
        drawChessBoard.drawBoardHighlighted(Objects.requireNonNullElse(isConnectedWhite, true));
    }
}
