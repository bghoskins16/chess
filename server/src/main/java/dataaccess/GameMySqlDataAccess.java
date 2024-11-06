package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class GameMySqlDataAccess extends MySqlDataAccess implements GameDataAccess {

    public GameMySqlDataAccess() throws DataAccessException {
        super();
        configureDatabase(createStatements);
    }

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear() {
        String statement = "TRUNCATE TABLE game";
        executeUpdate(statement);
    }

    //createGame: Create a new game.
    public int createGame(String gameName) {
        String newGame = new Gson().toJson(new ChessGame());
        String statement = "INSERT INTO game (gameName, gameData) VALUES (\"" + gameName + "\", '" + newGame + "')";

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                preparedStatement.executeUpdate();
                var rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return -1;
    }

    //getGame: Retrieve a specified game with the given game ID.
    public GameData getGame(int gameID) {
        String whiteUser, blackUser, gameName;
        ChessGame game;

        String statement = "SELECT * FROM game WHERE id=\"" + gameID + "\"";

        //Send information if dataset comes back empty return null, otherwise return the dataset
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                var rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    whiteUser = rs.getString("whiteUsername");
                    blackUser = rs.getString("blackUsername");
                    gameName = rs.getString("gameName");
                    game = new Gson().fromJson(rs.getString("gameData"), ChessGame.class);
                    return new GameData(gameID, whiteUser, blackUser, gameName, game);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    //listGames: Retrieve all games.
    public Collection<GameData> listGames() {
        Collection<GameData> games = new ArrayList<>();
        String statement = "SELECT * FROM game";

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                var rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    games.add(new GameData(
                                    rs.getInt("id"),
                                    rs.getString("whiteUsername"),
                                    rs.getString("blackUsername"),
                                    rs.getString("gameName"),
                                    new Gson().fromJson(rs.getString("gameData"), ChessGame.class)
                            )
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return games;
    }

    //updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID. This is used when players join a game or when a move is made.
    public void updateGame(GameData oldGameData, GameData newGameData) {
        String statement =
                "UPDATE game SET whiteUsername = \"" +
                        newGameData.whiteUsername() +
                        "\", blackUsername = \"" +
                        newGameData.blackUsername() +
                        "\", gameData = '" +
                        new Gson().toJson(newGameData.game()) +
                        "' WHERE id=" +
                        oldGameData.gameID();
        executeUpdate(statement);
    }

    public final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS game (
              `id` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256) DEFAULT NULL,
              `blackUsername` varchar(256) DEFAULT NULL,
              `gameName` varchar(256) NOT NULL,
              `gameData` TEXT NOT NULL,
              PRIMARY KEY (`id`)
            )
            """
    };
}
