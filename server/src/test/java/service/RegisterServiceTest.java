package service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;

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
