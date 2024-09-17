package chess;

import java.util.Collection;

public class BishopMoveCalculator {

    public Collection<ChessMove> pieceMoveCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new Collection<ChessMove>;
        
        int startRow = myPosition.getRow();
        int startCol = myPosition.getCol();

        int endRow = -1;
        int endCol = -1;
        ChessPosition endPos = null;

        // Try up and to the right
        
        endRow = startRow;
        endCol = startCol;

        // Keep moving until you hit a wall or another piece
        while (endRow < 0 && endCol < 0) {
            // Change the move Location
            endRow--;
            endCol--;

            // if this is not my own piece add to Collection
            endPos = new ChessPosition(endRow, endCol);
            moves.add(new ChessMove(myPosition, endPos));
            // if this is not an empty space then break
        
        }

        // Try up and to the left

        // Try down and to the right

        // Try down and to the left

        return moves;
    }

}