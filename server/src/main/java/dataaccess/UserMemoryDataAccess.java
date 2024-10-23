package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class UserMemoryDataAccess implements UserDataAccess{
    final private Collection<UserData> users = new ArrayList<>();

    int nextGameID = 0;

    //clear: A method for clearing all data from the database. This is used during testing.
    public void clear(){
        users.clear();
    }

    //createUser: Create a new user.
    public void createUser(UserData newUser){
        System.out.println("before create user with " + users.size() + " users");
        users.add(newUser);
        System.out.println("after create user with " + users.size() + " users");
    }

    //getUser: Retrieve a user with the given username.
    public UserData getUser(String username){
        System.out.println("get user with " + users.size() + " users");
        for (UserData user : users) {
            System.out.println("compare " + user.username() + " to " + username);
            if (user.username().equals(username)){
                return user;
            }
        }
        return null;
    }
}
