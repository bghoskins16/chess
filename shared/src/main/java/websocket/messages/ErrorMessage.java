package websocket.messages;

import chess.ChessGame;

public class ErrorMessage extends ServerMessage {
    String errorMessage;

    public ErrorMessage(String message) {
        super(ServerMessageType.ERROR);
        errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
