import chess.*;
import dataaccess.*;
import server.Server;
import service.Service;



public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);

        // Change database type to "Memory" or "SQL" (Default: "Memory")
        final String dataType = "SQL";

        try {
            UserDataAccess userDatabase = new UserMemoryDataAccess();
            AuthDataAccess authDatabase = new AuthMemoryDataAccess();
            GameDataAccess gameDatabase = new GameMemoryDataAccess();
            if (dataType.equals("SQL")) {
                userDatabase = new UserMySqlDataAccess();
                authDatabase = new AuthMySqlDataAccess();
                gameDatabase = new GameMySqlDataAccess();
            }
            new Service(userDatabase, authDatabase, gameDatabase);

            var port = 8080;
            new Server().run(port);
            System.out.printf("Server started on port %d", port);

        } catch (Exception e) {
            System.out.printf("Unable to start server: %s%n", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}