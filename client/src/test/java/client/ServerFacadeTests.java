package client;

import communication.ServerFacade;
import org.junit.jupiter.api.*;
import server.Server;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    static ServerFacade facade;
    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void reset() {
        facade.clear();
    }


    @Test
    public void registerTest() {
        var authToken = facade.register("user1", "pass1", "email1");
        assertTrue(authToken.length() > 10);
    }

    @Test
    public void registerFailTest() {
        facade.register("user1", "pass1", "email1");
        var authToken = facade.register("user1", "pass1", "email1");
        assertNull(authToken);
    }

    @Test
    public void logoutTest() {
        var authToken = facade.register("user1", "pass1", "email1");
        boolean successful = facade.logout(authToken);
        assertTrue(successful);
    }

    @Test
    public void logoutFailTest() {
        facade.register("user1", "pass1", "email1");
        boolean successful = facade.logout("Bad Auth Token");
        assertFalse(successful);
    }

    @Test
    public void loginTest() {
        var authToken = facade.register("user1", "pass1", "email1");
        facade.logout(authToken);
        var authToken2 = facade.login("user1", "pass1");
        assertNotEquals(authToken, authToken2);
        assertTrue(authToken2.length() > 10);
    }

    @Test
    public void loginFailTest() {
        var authToken = facade.register("user1", "pass1", "email1");
        facade.logout(authToken);
        var authToken2 = facade.login("user1", "Wrong password");
        assertNull(authToken2);
    }

    @Test
    public void createTest() {
        var authToken = facade.register("user1", "pass1", "email1");
        boolean success = facade.createGame(authToken, "game1");

        assertTrue(success);
    }

    @Test
    public void createFailTest() {
        facade.register("user1", "pass1", "email1");
        boolean success = facade.createGame("Bad Auth", "game1");

        assertFalse(success);
    }

    @Test
    public void listTest() {
        var authToken = facade.register("user1", "pass1", "email1");
        facade.createGame(authToken, "game1");
        facade.createGame(authToken, "game2");
        facade.createGame(authToken, "game3");
        facade.createGame(authToken, "game4");
        String list = facade.listGames(authToken);
        var games = list.split("\n");
        assertEquals(4, games.length);
    }

    @Test
    public void listEmptyTest() {
        var authToken = facade.register("user1", "pass1", "email1");
        String list = facade.listGames(authToken);
        assertEquals("", list);
    }

    @Test
    public void joinTest() {
        var authToken = facade.register("user1", "pass1", "email1");
        facade.createGame(authToken, "game1");
        facade.listGames(authToken);
        boolean success = facade.joinGame(authToken, "1", "white");

        assertTrue(success);
    }

    @Test
    public void joinFailTest() {
        var authToken = facade.register("user1", "pass1", "email1");
        facade.listGames(authToken);
        facade.joinGame(authToken, "1", "white");
        boolean success = facade.joinGame(authToken, "1", "white");

        assertFalse(success);
    }

    @Test
    public void clearUsersTest() {
        var authToken = facade.register("user1", "pass1", "email1");
        facade.logout(authToken);

        facade.clear();
        var authAfterClear = facade.login("user1", "pass1");
        assertNull(authAfterClear);
    }

}
