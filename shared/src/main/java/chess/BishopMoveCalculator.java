package chess;

import java.util.Collection;
import java.util.List;

import java.io.*;
import java.util.*;

public class BishopMoveCalculator extends PieceMoveCalculator {

    @Override
    public Collection<ChessMove> pieceMoveCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new LinkedList<>();
        
        int startRow = myPosition.getRow();
        int startCol = myPosition.getColumn();

        int endRow;
        int endCol;
        ChessPosition endPos;

        // Try up and to the right
        endRow = startRow;
        endCol = startCol;

        // Keep moving until you hit a wall or another piece
        while (endRow > 0 && endCol > 0) {
            // Change the move Location
            endRow--;
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
        }

        // Try up and to the left

        // Try down and to the right

        // Try down and to the left

        return moves;
    }

}