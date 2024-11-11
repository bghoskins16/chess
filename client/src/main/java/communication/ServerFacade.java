package communication;

public class ServerFacade {

    public ServerFacade() {
    }

    public void clear() {

    }

    public String register(String username, String password, String email) {
        System.out.println("register: " + username + ", " + password + ", " + email);
        return "auth";
    }

    public String login(String username, String password) {
        System.out.println("login: " + username + ", " + password);
        return "auth";
    }

    public void logout() {

    }

    public void listGames() {

    }

    public void createGame(String name) {

    }

    public void joinGame(String id, String color) {
        //convert id to an int
    }
}
