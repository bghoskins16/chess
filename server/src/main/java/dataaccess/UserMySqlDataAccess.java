package dataaccess;

import model.UserData;

public class UserMySqlDataAccess extends MySqlDataAccess implements UserDataAccess {

    public UserMySqlDataAccess() throws DataAccessException {
        super();
    }

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear() {
        String statement = "TRUNCATE TABLE user";
    }

    //createUser: Create a new user.
    public void createUser(UserData newUser) {
        String statement = "INSERT INTO user VALUES (\"" + newUser.username() + "\", \"" + newUser.password()+ "\", \"" + newUser.email() + "\")";

    }

    //getUser: Retrieve a user with the given username.
    public UserData getUser(String username) {
        String statement  = "SELECT * FROM user WHERE username=\"" + username + "\"";
        //Send information if dataset comes back empty return null, otherwise return the dataset
        return null;
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
