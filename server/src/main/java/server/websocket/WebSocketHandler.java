package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import server.ResponseException;
import service.WebsocketService;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final WebsocketService service = new WebsocketService();
    Gson serializer = new Gson();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.printf("Received: %s\n", message);

        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            case CONNECT -> connect(action.getAuthToken(), action.getGameID(), session);
            case MAKE_MOVE -> makeMove(message, session);
            case LEAVE -> leave(action.getAuthToken(), action.getGameID(), session);
            case RESIGN -> resign(action.getAuthToken(), action.getGameID(), session);
        }
    }

    private void connect(String authToken, Integer gameID, Session session) throws IOException {

        try {
            AuthData authData = service.authenticate(authToken);
            GameData gameData = service.getGame(gameID);
            connections.add(authData.username(), gameID, session);
            // Send a join notification to all other users in the game
            connections.broadcast(
                    authData.username(),
                    gameID,
                    serializer.toJson(new NotificationMessage(authData.username() + " is now in the game"))
            );

            // Send a LOAD_GAME command back to the root client
            session.getRemote().sendString(serializer.toJson(new LoadGameMessage(gameData.game())));
        } catch (ResponseException ex) {
            session.getRemote().sendString(serializer.toJson(new ErrorMessage(ex.getMessage())));
        }
    }

    private void makeMove(String message, Session session) throws IOException {

        MakeMoveCommand action = new Gson().fromJson(message, MakeMoveCommand.class);

        try {
            AuthData authData = service.authenticate(action.getAuthToken());

            if (service.isObserver(action.getGameID(), authData.username())){
                session.getRemote().sendString(
                        serializer.toJson(new ErrorMessage("Error: observers can't make moves")));
                return;
            }

            GameData gameData = service.makeMove(action.getGameID(), authData ,action.getMove());
            // Send a move notification to all other users in the game
            connections.broadcast(
                    authData.username(),
                    action.getGameID(),
                    serializer.toJson(new NotificationMessage(authData.username() + " made a move"))
            );

            // Send a LOAD_GAME command to ALL users
            connections.broadcast("",
                    action.getGameID(),
                    serializer.toJson(new LoadGameMessage(gameData.game()))
            );

            //check for check, checkmate, and stalemate
            if (gameData.game().isInCheckmate(ChessGame.TeamColor.WHITE)) {
                connections.broadcast("",
                        action.getGameID(),
                        serializer.toJson(new NotificationMessage("CHECKMATE!! " + gameData.whiteUsername() + " WINS!"))
                );
                service.endGame(action.getGameID());
            } else if (gameData.game().isInCheckmate(ChessGame.TeamColor.BLACK)) {
                connections.broadcast("",
                        action.getGameID(),
                        serializer.toJson(new NotificationMessage("CHECKMATE!! " + gameData.blackUsername() + " WINS!"))
                );
                service.endGame(action.getGameID());
            } else if (gameData.game().isInCheck(gameData.game().getTeamTurn())) {
                connections.broadcast("", action.getGameID(), serializer.toJson(new NotificationMessage("CHECK!")));
            } else if (gameData.game().isInCheckmate(gameData.game().getTeamTurn())) {
                connections.broadcast("", action.getGameID(), serializer.toJson(new NotificationMessage("STALEMATE. GAME OVER")));
                service.endGame(action.getGameID());
            }

        } catch (ResponseException ex) {
            System.out.println(ex.getMessage());
            session.getRemote().sendString(serializer.toJson(new ErrorMessage(ex.getMessage())));
        }
    }

    private void leave(String authToken, Integer gameID, Session session) throws IOException {

        try {
            AuthData authData = service.authenticate(authToken);
            service.leaveGame(gameID, authData.username());
            connections.remove(authData.username());
            // Send a leave notification to all other users in the game
            connections.broadcast(authData.username(), gameID, serializer.toJson(new NotificationMessage(authData.username() + " left the game")));
        } catch (ResponseException ex) {
            session.getRemote().sendString(serializer.toJson(new ErrorMessage(ex.getMessage())));
        }
    }

    private void resign(String authToken, Integer gameID, Session session) throws IOException {

        try {
            AuthData authData = service.authenticate(authToken);

            if (service.isObserver(gameID, authData.username())){
                session.getRemote().sendString(serializer.toJson(new ErrorMessage("Error: observers can't resign")));
                return;
            }

            service.endGame(gameID);
            // Send a resignation notification to ALL users in the game
            connections.broadcast("",
                    gameID,
                    serializer.toJson(new NotificationMessage(authData.username() + " has resigned from the game"))
            );
        } catch (ResponseException ex) {
            session.getRemote().sendString(serializer.toJson(new ErrorMessage(ex.getMessage())));
        }
    }
}