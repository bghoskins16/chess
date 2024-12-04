package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import response.ErrorResponse;
import server.ResponseException;
import service.WebsocketService;
import websocket.commands.UserGameCommand;
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


        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            case CONNECT -> connect(action.getAuthToken(), action.getGameID(), session);
            case MAKE_MOVE -> System.out.println("Received a make move");
            case LEAVE -> System.out.println("Received a leave");
            case RESIGN -> message = "{ \"Notification\": \"resigned\" }";
        }

        session.getRemote().sendString(message);
    }

    private void connect(String auth, Integer gameID, Session session) throws IOException {
        System.out.println("Received a connect" +session.toString());

        try{
            String username = service.connect(auth,gameID);
            connections.add(username, session);
            connections.broadcast(username, "{ \"Notification\": \""+ username + " is now in the game\" }");
        } catch (ResponseException ex) {
            session.getRemote().sendString(serializer.toJson(new ErrorResponse(ex.getMessage())));
        }
    }
}