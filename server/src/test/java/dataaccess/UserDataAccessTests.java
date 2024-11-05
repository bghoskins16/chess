package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import service.GameService;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserDataAccessTests {

    static UserDataAccess userDatabase;
    static AuthDataAccess authDatabase;


    UserData user;
    AuthData auth;

    @BeforeEach
    void clear() {
        try {
            userDatabase = new UserMySqlDataAccess();
            authDatabase = new AuthMySqlDataAccess();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        userDatabase.clear();
        user = new UserData("user", "pass", "mail");
//        userDatabase.createUser(user);
//        auth = authDatabase.createAuth(user.username());
    }

    @Test
    void createUserGood() {
        UserData userRecv;
        try {
            userDatabase.createUser(user);
            userRecv = userDatabase.getUser(user.username());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(user.username(), userRecv.username());
        assertEquals(user.password(), userRecv.password());
        assertEquals(user.email(), userRecv.email());
    }

    @Test
    void createUserBad() {
        UserData userRecv;
        try {
            userDatabase.createUser(user);
            userDatabase.createUser(user);
            userRecv = userDatabase.getUser(user.username());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(user.username(), userRecv.username());
        assertEquals(user.password(), userRecv.password());
        assertEquals(user.email(), userRecv.email());
    }

    @Test
    void getUserGood() {
        UserData user1 = new UserData("user1", "pass1", "mail1");
        UserData user2 = new UserData("user2", "pass2", "mail2");
        UserData user3 = new UserData("user3", "pass3", "mail3");


        UserData userRecv1;
        UserData userRecv2;
        UserData userRecv3;


        try {
            userDatabase.createUser(user1);
            userDatabase.createUser(user2);
            userDatabase.createUser(user3);

            userRecv1 = userDatabase.getUser(user1.username());
            userRecv2 = userDatabase.getUser(user2.username());
            userRecv3 = userDatabase.getUser(user3.username());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(user1.username(), userRecv1.username());
        assertEquals(user2.password(), userRecv2.password());
        assertEquals(user3.email(), userRecv3.email());
    }

    @Test
    void getUserBad() {
        UserData userRecv;

        try {
            userRecv = userDatabase.getUser("Bad Username");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertNull(userRecv);
    }

}
