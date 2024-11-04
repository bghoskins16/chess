package dataaccess;

import model.AuthData;

import java.util.UUID;

public class AuthMySqlDataAccess extends MySqlDataAccess implements AuthDataAccess {

    public AuthMySqlDataAccess() throws DataAccessException {
        super();
    }

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear() {
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
        return null;
    }

    //deleteAuth: Delete an authorization so that it is no longer valid.
    public void deleteAuth(AuthData authToken) {
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `username` varchar(256) NOT NULL UNIQUE,
              `auth` varchar(256) NOT NULL,
              PRIMARY KEY (`username`)
            )
            """
    };
}
