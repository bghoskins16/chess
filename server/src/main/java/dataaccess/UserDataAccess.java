package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;

public interface UserDataAccess {

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear();

    //createUser: Create a new user.
    public void createUser(UserData newUser);

    //getUser: Retrieve a user with the given username.
    public UserData getUser(String username);

}
