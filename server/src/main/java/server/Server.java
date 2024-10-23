package server;

import handler.*;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", ClearHandler::doHandle);
        Spark.post("/user", RegisterHandler::doHandle);
        Spark.post("/session", LoginHandler::doHandle);
        Spark.delete("/session", LogoutHandler::doHandle);
        Spark.get("/game", ListGamesHandler::doHandle);
        Spark.post("/game", CreateGameHandler::doHandle);
        Spark.put("/game", JoinGameHandler::doHandle);

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
