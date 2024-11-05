package dataaccess;

import model.AuthData;

import java.util.UUID;

public class AuthMySqlDataAccess extends MySqlDataAccess implements AuthDataAccess {

    public AuthMySqlDataAccess() throws DataAccessException {
        super();
        configureDatabase(createStatements);
    }

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear() {
        String statement = "TRUNCATE TABLE auth";
        executeUpdate(statement);
    }

    //createAuth: Create a new authorization.
    public AuthData createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        String statement = "INSERT INTO auth VALUES (\"" + username + "\", \"" + authToken + "\")";

        // TODO: Add a try catch
        executeUpdate(statement);
        return new AuthData(username, authToken);
    }

    //getAuth: Retrieve an authorization given an authToken.
    public AuthData getAuth(String authToken) {
        String username;
        String statement  = "SELECT * FROM auth WHERE authToken=\"" + authToken + "\"";

        //Send information if dataset comes back empty return null, otherwise return the dataset
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                var rs = preparedStatement.executeQuery();
                rs.next();
                username = rs.getString("username");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    //deleteAuth: Delete an authorization so that it is no longer valid.
    public void deleteAuth(AuthData authToken) {
        String statement = "DELETE FROM auth WHERE authToken=\"" + authToken + "\"";
        executeUpdate(statement);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `username` varchar(256) NOT NULL UNIQUE,
              `authToken` varchar(256) NOT NULL,
              PRIMARY KEY (`username`)
            )
            """
    };
}
