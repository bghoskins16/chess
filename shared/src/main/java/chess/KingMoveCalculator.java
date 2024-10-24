package chess;

import java.util.Collection;
import java.util.LinkedList;

public class KingMoveCalculator extends PieceMoveCalculator {

    @Override
    public Collection<ChessMove> pieceMoveCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new LinkedList<>();

        int startRow = myPosition.getRow();
        int startCol = myPosition.getColumn();

        ChessPosition endPos;

        // Try up and to the right
        if (startRow > 1 && startCol > 1) {
            endPos = new ChessPosition(startRow - 1, startCol - 1);
            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    moves.add(new ChessMove(myPosition, endPos, null));
                }
            } else {
                moves.add(new ChessMove(myPosition, endPos, null));
            }
        }

        // Try up
        if (startRow > 1) {
            endPos = new ChessPosition(startRow - 1, startCol);
            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    moves.add(new ChessMove(myPosition, endPos, null));
                }
            } else {
                moves.add(new ChessMove(myPosition, endPos, null));
            }
        }

        // Try up and to the left
        if (startRow > 1 && startCol < 8) {
            endPos = new ChessPosition(startRow - 1, startCol + 1);
            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    moves.add(new ChessMove(myPosition, endPos, null));
                }
            } else {
                moves.add(new ChessMove(myPosition, endPos, null));
            }
        }

        // Try right
        if (startCol > 1) {
            endPos = new ChessPosition(startRow, startCol - 1);
            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    moves.add(new ChessMove(myPosition, endPos, null));
                }
            } else {
                moves.add(new ChessMove(myPosition, endPos, null));
            }
        }

        // Try left
        if (startCol < 8) {
            endPos = new ChessPosition(startRow, startCol + 1);
            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    moves.add(new ChessMove(myPosition, endPos, null));
                }
            } else {
                moves.add(new ChessMove(myPosition, endPos, null));
            }
        }

        // Try down and to the right
        if (startRow < 8 && startCol > 1) {
            endPos = new ChessPosition(startRow + 1, startCol - 1);
            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    moves.add(new ChessMove(myPosition, endPos, null));
                }
            } else {
                moves.add(new ChessMove(myPosition, endPos, null));
            }
        }

        // Try down
        if (startRow < 8) {
            endPos = new ChessPosition(startRow + 1, startCol);
            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    moves.add(new ChessMove(myPosition, endPos, null));
                }
            } else {
                moves.add(new ChessMove(myPosition, endPos, null));
            }
        }

        // Try down and to the left
        if (startRow < 8 && startCol < 8) {
            endPos = new ChessPosition(startRow + 1, startCol + 1);
            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    moves.add(new ChessMove(myPosition, endPos, null));
                }
            } else {
                moves.add(new ChessMove(myPosition, endPos, null));
            }
        }

        return moves;
    }

}