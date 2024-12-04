package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException  {
        System.out.printf("Received: %s\n", message);


        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            case CONNECT -> connect(action.getAuthToken(), session);
            case MAKE_MOVE -> System.out.println("Received a make move");
            case LEAVE -> System.out.println("Received a leave");
            case RESIGN -> message = "{ \"Notification\": \"resigned\" }";
        }

        session.getRemote().sendString(message);
    }

    private void connect(String auth, Session session) throws IOException {
        System.out.println("Received a connect" +session.toString());
        connections.add(auth, session);
        connections.broadcast(session.toString(), "{ \"Notification\": \"New Session\" }");
    }
}