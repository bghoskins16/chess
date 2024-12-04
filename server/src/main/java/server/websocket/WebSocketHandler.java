package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import response.ErrorResponse;
import server.ResponseException;
import service.WebsocketService;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    Gson serializer = new Gson();
    private final ConnectionManager connections = new ConnectionManager();
    private final WebsocketService service = new WebsocketService();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException  {
        System.out.printf("Received: %s\n", message);
        session.getRemote().sendString(message);  // TODO: need to delete

        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            case CONNECT -> connect(action.getAuthToken(), action.getGameID(), session);
            case MAKE_MOVE -> System.out.println("Received a make move");
            case LEAVE -> System.out.println("Received a leave");
            case RESIGN -> message = "{ \"Notification\": \"resigned\" }";
        }

        session.getRemote().sendString(message);
    }

    private void connect(String authToken, Integer gameID, Session session) throws IOException {
        System.out.println("Received a connect" +session.toString());

        try{
            AuthData authData = service.authenticate(authToken);
            GameData gameData = service.connect(gameID);
            connections.add(authData.username(), gameID, session);
            // Send a join notification to all other users in the game
            connections.broadcast(authData.username(), gameID, "{ \"Notification\": \""+ authData.username() + " is now in the game\" }");
            // Send a LOAD_GAME command back to the root client
            session.getRemote().sendString(serializer.toJson(new LoadGameMessage(gameData.game())));
        } catch (ResponseException ex) {
            session.getRemote().sendString(serializer.toJson(new ErrorMessage(ex.getMessage())));
        }
    }
}