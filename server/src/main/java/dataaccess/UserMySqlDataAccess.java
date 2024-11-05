package dataaccess;

import model.UserData;

import javax.swing.*;

public class UserMySqlDataAccess extends MySqlDataAccess implements UserDataAccess {

    public UserMySqlDataAccess() throws DataAccessException {
        super();
        configureDatabase(createStatements);
    }

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear() {
        String statement = "TRUNCATE TABLE user";

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //createUser: Create a new user.
    public void createUser(UserData newUser) {
        String statement = "INSERT INTO user VALUES (\"" + newUser.username() + "\", \"" + newUser.password() + "\", \"" + newUser.email() + "\")";

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //getUser: Retrieve a user with the given username.
    public UserData getUser(String username) {
        String password, email;
        String statement = "SELECT * FROM user WHERE username=\"" + username + "\"";

        //Send information if dataset comes back empty return null, otherwise return the dataset
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                var rs = preparedStatement.executeQuery();
                rs.next();
                password = rs.getString("password");
                email = rs.getString("email");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new UserData(username, password, email);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  user (
              `username` varchar(256) NOT NULL UNIQUE,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) DEFAULT NULL,
              PRIMARY KEY (`username`)
            )
            """
    };
}
