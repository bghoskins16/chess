package service;

import dataaccess.AuthMemoryDataAccess;
import dataaccess.UserMemoryDataAccess;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.LogoutRequest;
import request.RegisterRequest;
import server.ResponseException;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceTests {

    static UserService service = new UserService();
    static UserMemoryDataAccess userDatabase = new UserMemoryDataAccess();
    static AuthMemoryDataAccess authDatabase = new AuthMemoryDataAccess();

    @BeforeEach
    void clear() {
        service.clear();
    }

    @Test
    void registerUserGood() {
        RegisterRequest user = new RegisterRequest("user1", "pass1", "mail1");
        AuthData auth;
        try {
            auth = service.register(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Collection<UserData> userList = userDatabase.getUsers();
        Collection<AuthData> authList = authDatabase.getAuthTokens();
        assertEquals(1, userList.size());
        assertEquals(1, authList.size());
        assertEquals(userDatabase.getUser("user1").password(), (user.password()));
        assertEquals(auth.username(), user.username());
    }

    @Test
    void registerUserBad() {
        RegisterRequest user = new RegisterRequest("user1", "pass1", null);
        AuthData auth;
        try {
            auth = service.register(user);
        } catch (ResponseException e) {
            assertEquals(e.statusCode(), 400);
        }

        Collection<UserData> userList = userDatabase.getUsers();
        Collection<AuthData> authList = authDatabase.getAuthTokens();
        assertEquals(0, userList.size());
        assertEquals(0, authList.size());
    }


    @Test
    void loginUserGood() {
        UserData user = new UserData("user1", "pass1", "mail1");
        userDatabase.createUser(user);

        LoginRequest loginRequest = new LoginRequest("user1", "pass1");
        AuthData auth;
        try {
            auth = service.login(loginRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Collection<AuthData> authList = authDatabase.getAuthTokens();
        assertEquals(1, authList.size());
        assertEquals(auth.username(), user.username());
    }

    @Test
    void loginUserBad() {
        UserData user = new UserData("user1", "pass1", "mail1");
        userDatabase.createUser(user);

        LoginRequest loginRequest = new LoginRequest("user1", "pass2");
        AuthData auth;
        try {
            auth = service.login(loginRequest);
        } catch (ResponseException e) {
            assertEquals(e.statusCode(), 401);
        }

        Collection<AuthData> authList = authDatabase.getAuthTokens();
        assertEquals(0, authList.size());
    }

    @Test
    void logoutUserGood() {
        UserData user = new UserData("user1", "pass1", "mail1");
        userDatabase.createUser(user);
        AuthData auth = authDatabase.createAuth(user.username());

        LogoutRequest logoutRequest = new LogoutRequest(auth.authToken());
        try {
            service.logout(logoutRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Collection<AuthData> authList = authDatabase.getAuthTokens();
        assertEquals(0, authList.size());
    }

    @Test
    void logoutUserBad() {
        UserData user = new UserData("user1", "pass1", "mail1");
        userDatabase.createUser(user);
        authDatabase.createAuth(user.username());

        LogoutRequest logoutRequest = new LogoutRequest("Random Token");
        try {
            service.logout(logoutRequest);
        } catch (ResponseException e) {
            assertEquals(e.statusCode(), 401);
        }

        Collection<AuthData> authList = authDatabase.getAuthTokens();
        assertEquals(1, authList.size());
    }

    @Test
    void testClear() {
        UserData user1 = new UserData("user1", "pass1", "mail1");
        userDatabase.createUser(user1);
        authDatabase.createAuth(user1.username());
        UserData user2 = new UserData("user2", "pass2", "mail2");
        userDatabase.createUser(user2);
        authDatabase.createAuth(user2.username());


        service.clear();

        Collection<UserData> userList = userDatabase.getUsers();
        Collection<AuthData> authList = authDatabase.getAuthTokens();
        assertEquals(0, userList.size());
        assertEquals(0, authList.size());
    }
}
