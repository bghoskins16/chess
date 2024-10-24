package service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;

public class GameServiceTests {

    static final UserService service = new UserService();

    @BeforeEach
    void clear() {
        service.clear();
    }

    @Test
    void createGame(){
        RegisterRequest userToRegister = new RegisterRequest("user1" , "pass1", "mail1");
        RegisterRequest userRegistered;
        try {
            service.register(userToRegister);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


//        assertEquals(1, pets.size());
//        assertTrue(pets.contains(pet));
    }
}
