package server;

/**
 * Indicates there was an error connecting to the database
 */
public class ResponseException extends Exception{
    final private int statusCode;

    public ResponseException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int StatusCode() {
        return statusCode;
    }
}