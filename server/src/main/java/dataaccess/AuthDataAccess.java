package dataaccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;

public interface AuthDataAccess {

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear();

    //createAuth: Create a new authorization.
    public AuthData createAuth(String username);

    //getAuth: Retrieve an authorization given an authToken.
    public AuthData getAuth(String authToken);

    //deleteAuth: Delete an authorization so that it is no longer valid.
    public void deleteAuth(AuthData authToken);
}
