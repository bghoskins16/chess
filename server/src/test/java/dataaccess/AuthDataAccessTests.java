package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthDataAccessTests {

    static AuthDataAccess authDatabase;

    @BeforeEach
    void clear() {
        try {
            authDatabase = new AuthMySqlDataAccess();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        authDatabase.clear();
    }

    @Test
    void createAuth() {
        AuthData authRecv1;
        AuthData authRecv2;
        try {
            authRecv1 = authDatabase.createAuth("user1");
            authRecv2 = authDatabase.getAuth(authRecv1.authToken());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(authRecv1.authToken(), authRecv2.authToken());
        assertEquals(authRecv1.username(), authRecv2.username());
        assertEquals("user1", authRecv2.username());
    }

    @Test
    void createAuthDuplicate() {
        AuthData authRecv1 = null;
        AuthData authRecv2 = null;
        try {
            authRecv1 = authDatabase.createAuth("User1");
            authRecv2 = authDatabase.createAuth("User1");
        } catch (Exception e) {
            assertNull(authRecv2);
        }

        assertNotEquals(authRecv1, authRecv2);
    }

    @Test
    void getAuthtest() {
        AuthData authRecv1;
        AuthData authRecv2;
        AuthData authRecv3;
        try {
            authRecv1 = authDatabase.createAuth("User1");
            authRecv2 = authDatabase.createAuth("User2");
            authRecv3 = authDatabase.createAuth("User3");

            authRecv3 = authDatabase.getAuth(authRecv3.authToken());
            authRecv2 = authDatabase.getAuth(authRecv2.authToken());
            authRecv1 = authDatabase.getAuth(authRecv1.authToken());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals("User1", authRecv1.username());
        assertEquals("User2", authRecv2.username());
        assertEquals("User3", authRecv3.username());
    }

    @Test
    void getAuthNotAdded() {
        AuthData authRecv;

        try {
            authRecv = authDatabase.getAuth("Not a Real Auth Token");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertNull(authRecv);
    }

    @Test
    void deleteAuth() {
        AuthData auth;
        AuthData authRecv1;
        AuthData authRecv2;

        try {
            auth = authDatabase.createAuth("User1");
            authRecv1 = authDatabase.getAuth(auth.authToken());
            authDatabase.deleteAuth(auth);
            authRecv2 = authDatabase.getAuth(auth.authToken());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals("User1", authRecv1.username());
        assertNull(authRecv2);
    }

    @Test
    void deleteAuthNotAdded() {
        AuthData auth = new AuthData("Not Real User", "With No Auth");

        try {
            authDatabase.deleteAuth(auth);
        } catch (Exception e) {
            fail();
            throw new RuntimeException(e);
        }
    }

    @Test
    void clearAuthsTest() {
        UserData userRecvBefore;
        AuthData authRecv1;
        AuthData authRecv2;
        AuthData authRecv3;

        try {
            authRecv1 = authDatabase.createAuth("User1");
            authRecv2 = authDatabase.createAuth("User2");
            authRecv3 = authDatabase.createAuth("User3");

            authDatabase.clear();

            authRecv3 = authDatabase.getAuth(authRecv3.authToken());
            authRecv2 = authDatabase.getAuth(authRecv2.authToken());
            authRecv1 = authDatabase.getAuth(authRecv1.authToken());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertNull(authRecv1);
        assertNull(authRecv2);
        assertNull(authRecv3);
    }
}
