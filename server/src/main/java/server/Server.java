package server;

import handler.*;
import server.websocket.WebSocketHandler;
import spark.*;

public class Server {
    private final WebSocketHandler webSocketHandler;

    public Server() {
        webSocketHandler = new WebSocketHandler();
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.webSocket("/ws", webSocketHandler);

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", Handler::clearHandler);
        Spark.post("/user", UserHandler::registerHandler);
        Spark.post("/session", UserHandler::loginHandler);
        Spark.delete("/session", UserHandler::logoutHandler);
        Spark.get("/game", GameHandler::listGamesHandler);
        Spark.post("/game", GameHandler::createGameHandler);
        Spark.put("/game", GameHandler::joinGameHandler);
        Spark.exception(ResponseException.class, Handler::exceptionHandler);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        //Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
