package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
import websocket.messages.ServerMessage;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import java.io.IOException;

public class ConnectionManager {

    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String userName, Integer gameID, Session session) {
        var connection = new Connection(userName, gameID, session);
        connections.put(userName, connection);
    }

    public void remove(String userName) {
        connections.remove(userName);
    }

    public void broadcast(String excludeUserName, Integer gameID, String message) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                // Only Broadcast to the other users in this game
                if (Objects.equals(c.gameID, gameID) && !c.userName.equals(excludeUserName)) {
                    c.send(message);
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.userName);
        }
    }
}
