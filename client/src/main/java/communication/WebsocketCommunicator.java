package communication;

import chess.ChessMove;
import com.google.gson.Gson;
import websocket.commands.MakeMoveCommand;
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

    // CONNECT
    public void connect(int gameID, String auth) throws Exception {
        this.session.getBasicRemote().sendText(new Gson().toJson(new UserGameCommand(UserGameCommand.CommandType.CONNECT, auth, gameID)));
    }

    //MAKE_MOVE
    public void makeMove(int gameID, String auth, ChessMove move) throws Exception {
        this.session.getBasicRemote().sendText(new Gson().toJson(new MakeMoveCommand(auth, gameID, move)));
    }

    //LEAVE
    public void leave(int gameID, String auth) throws Exception {
        this.session.getBasicRemote().sendText(new Gson().toJson(new UserGameCommand(UserGameCommand.CommandType.LEAVE, auth, gameID)));
    }

    //RESIGN
    public void resign(int gameID, String auth) throws Exception {
        this.session.getBasicRemote().sendText(new Gson().toJson(new UserGameCommand(UserGameCommand.CommandType.RESIGN, auth, gameID)));
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}