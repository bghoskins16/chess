package service;
import dataaccess.MemoryDataAccess;
import dataaccess.UserDataAccess;
import dataaccess.UserMemoryDataAccess;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {

    static final UserService service = new UserService();

    @BeforeEach
    void clear() {
        service.clear();
    }

    @Test
    void createGame(){
        RegisterRequest user = new RegisterRequest("user1" , "pass1", "mail1");
        try {
            service.register(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        assertEquals(1, pets.size());
//        assertTrue(pets.contains(pet));
    }
}
