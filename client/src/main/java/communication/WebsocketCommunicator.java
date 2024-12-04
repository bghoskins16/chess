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
import websocket.messages.LoadGameMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.net.URI;

public class WebsocketCommunicator extends Endpoint {

    Session session;
    ServerMessageObserver serverMessageObserver;

    public WebsocketCommunicator(ServerMessageObserver serverMessageObserver) throws Exception {
        URI uri = new URI("ws://localhost:8080/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);
        this.serverMessageObserver = serverMessageObserver;

        //set message handler
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                LoadGameMessage serverMessage = new Gson().fromJson(message, LoadGameMessage.class);
                serverMessageObserver.notify(serverMessage);
            }
        });
    }

    public void connect(int gameID, String auth) throws Exception {
        this.session.getBasicRemote().sendText(new Gson().toJson(new UserGameCommand(UserGameCommand.CommandType.CONNECT, auth, gameID)));
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}