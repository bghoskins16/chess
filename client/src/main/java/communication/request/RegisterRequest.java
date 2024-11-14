package communication.request;

public record RegisterRequest(
        String username,
        String password,
        String email) {
}

