package chess;

import java.util.Collection;
import java.util.LinkedList;

public class RookMoveCalculator extends PieceMoveCalculator {

    @Override
    public Collection<ChessMove> pieceMoveCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new LinkedList<>();
        
        int startRow = myPosition.getRow();
        int startCol = myPosition.getColumn();

        int endRow;
        int endCol;
        ChessPosition endPos;

        // Try up
        endRow = startRow;
        endCol = startCol;
        while (endRow > 1) {
            // Change the move Location
            endRow--;
            endPos = new ChessPosition(endRow, endCol);

            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    moves.add(new ChessMove(myPosition, endPos, null));
                }
                break;
            }
            else {
                moves.add(new ChessMove(myPosition, endPos, null));
            }
        }

        // Try down
        endRow = startRow;
        endCol = startCol;
        while (endRow < 8) {
            // Change the move Location
            endRow++;
            endPos = new ChessPosition(endRow, endCol);

            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    moves.add(new ChessMove(myPosition, endPos, null));
                }
                break;
            }
            else {
                moves.add(new ChessMove(myPosition, endPos, null));
            }
        }

        // Try left
        endRow = startRow;
        endCol = startCol;
        while (endCol > 1) {
            // Change the move Location
            endCol--;
            endPos = new ChessPosition(endRow, endCol);

            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    moves.add(new ChessMove(myPosition, endPos, null));
                }
                break;
            }
            else {
                moves.add(new ChessMove(myPosition, endPos, null));
            }
        }

        // Try right
        endRow = startRow;
        endCol = startCol;
        while (endCol < 8) {
            // Change the move Location
            endCol++;
            endPos = new ChessPosition(endRow, endCol);

            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    moves.add(new ChessMove(myPosition, endPos, null));
                }
                break;
            }
            else {
                moves.add(new ChessMove(myPosition, endPos, null));
            }
        }

        return moves;
    }

}