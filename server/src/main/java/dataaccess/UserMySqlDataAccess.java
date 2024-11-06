package dataaccess;

import model.UserData;

import javax.swing.*;

public class UserMySqlDataAccess extends MySqlDataAccess implements UserDataAccess {

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  user (
              `id` int NOT NULL AUTO_INCREMENT,
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) DEFAULT NULL,
              PRIMARY KEY (`id`)
            )
            """
    };

    public UserMySqlDataAccess() throws DataAccessException {
        super();
        configureDatabase(createStatements);
    }

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear() {
        String statement = "TRUNCATE TABLE user";
        executeUpdate(statement);
    }

    //createUser: Create a new user.
    public void createUser(UserData newUser) {
        String statement = "INSERT INTO user (username, password, email) VALUES (\"" +
                newUser.username() + "\", \"" +
                newUser.password() + "\", \"" +
                newUser.email() + "\")";
        executeUpdate(statement);
    }

    //getUser: Retrieve a user with the given username.
    public UserData getUser(String username) {
        String password, email;
        String statement = "SELECT * FROM user WHERE username=\"" + username + "\"";

        //Send information if dataset comes back empty return null, otherwise return the dataset
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(statement)) {
                var rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    password = rs.getString("password");
                    email = rs.getString("email");
                    return new UserData(username, password, email);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;

    }
}
