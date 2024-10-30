package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class UserMemoryDataAccess implements UserDataAccess {
    static private Collection<UserData> users = new ArrayList<>();

    int nextGameID = 0;

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear() {
        users.clear();
    }

    //createUser: Create a new user.
    public void createUser(UserData newUser) {
        users.add(newUser);
    }

    //getUser: Retrieve a user with the given username.
    public UserData getUser(String username) {
        for (UserData user : users) {
            if (user.username().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Collection<UserData> getUsers() {
        return users;
    }
}
