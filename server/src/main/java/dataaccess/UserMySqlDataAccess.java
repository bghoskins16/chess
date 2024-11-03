package dataaccess;

import model.UserData;

public class UserMySqlDataAccess implements UserDataAccess {

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear() {
    }

    //createUser: Create a new user.
    public void createUser(UserData newUser) {
    }

    //getUser: Retrieve a user with the given username.
    public UserData getUser(String username) {
        return null;
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  user (
              `username` varchar(256) NOT NULL UNIQUE,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) DEFAULT NULL,
              PRIMARY KEY (`username`),
            )
            """
    };
}
