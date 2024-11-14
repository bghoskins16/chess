package communication.request;

public record CreateGameRequest(
        String authToken,
        String gameName) {
}

