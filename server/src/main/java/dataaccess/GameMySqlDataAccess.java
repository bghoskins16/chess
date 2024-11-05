package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.util.Collection;

public class GameMySqlDataAccess extends MySqlDataAccess implements GameDataAccess {

    public GameMySqlDataAccess() throws DataAccessException {
        super();
        configureDatabase(createStatements);
    }

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear() {
        String statement = "TRUNCATE TABLE game";
    }

    //createGame: Create a new game.
    public int createGame(String gameName) {
        String newGame = new Gson().toJson(new ChessGame());
        String statement = "INSERT INTO game (gameName, gameData) VALUES (\"" + gameName + "\", \"" + newGame + "\")";
        return 0;
    }

    //getGame: Retrieve a specified game with the given game ID.
    public GameData getGame(int gameID) {
        String statement = "SELECT * FROM game WHERE id=\"" + gameID + "\"";
        //Send information if dataset comes back empty return null, otherwise return the dataset
        return null;
    }

    //listGames: Retrieve all games.
    public Collection<GameData> listGames() {
        String statement = "SELECT * FROM game";
        return null;
    }

    //updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID. This is used when players join a game or when a move is made.
    public void updateGame(GameData oldGameData, GameData newGameData) {
        String statement = "UPDATE game SET whiteUsername = \"" + newGameData.whiteUsername() + "\", blackUsername = \"" + newGameData.blackUsername() + "\" WHERE id=" + oldGameData.gameID();
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
