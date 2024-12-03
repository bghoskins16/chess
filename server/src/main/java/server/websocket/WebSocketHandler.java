package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

@WebSocket
public class WebSocketHandler {

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        System.out.printf("Received: %s\n", message);


        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        switch (action.getCommandType()) {
            case CONNECT -> System.out.println("Received a connect");
            case MAKE_MOVE -> System.out.println("Received a make move");
            case LEAVE -> System.out.println("Received a leave");
            case RESIGN -> message = "{ \"Notification\": \"resigned\" }";
        }

        session.getRemote().sendString(message);
    }
}