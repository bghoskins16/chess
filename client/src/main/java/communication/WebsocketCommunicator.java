package communication;

//public class WebsocketCommunicator {
//
//    //CONNECT
//
//    //MAKE_MOVE
//
//    //LEAVE
//
//    //RESIGN
//}


import com.google.gson.Gson;
import websocket.commands.UserGameCommand;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

public class WebsocketCommunicator extends Endpoint {

    public static void main(String[] args) throws Exception {
        var ws = new WebsocketCommunicator();
        Scanner scanner = new Scanner(System.in);

        ws.connect(1, "05a827ba-e74f-46cb-999a-a3a929488783");
    }

    public Session session;

    public WebsocketCommunicator() throws Exception {
        URI uri = new URI("ws://localhost:8080/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                System.out.println(message);
            }
        });
    }

    public void connect(int gameID, String auth) throws Exception {
        this.session.getBasicRemote().sendText(new Gson().toJson(new UserGameCommand(UserGameCommand.CommandType.CONNECT, auth, gameID)));
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}