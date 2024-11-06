package chess;

import java.util.Collection;
import java.util.LinkedList;

public class KnightMoveCalculator extends PieceMoveCalculator {

    @Override
    public Collection<ChessMove> pieceMoveCalculator(
            ChessGame.TeamColor pieceColor,
            ChessPiece.PieceType type,
            ChessBoard board,
            ChessPosition myPosition) {

        Collection<ChessMove> moves = new LinkedList<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        singleCalc(row + 2, col + 1, moves, pieceColor, board, myPosition);
        singleCalc(row + 2, col - 1, moves, pieceColor, board, myPosition);
        singleCalc(row - 2, col + 1, moves, pieceColor, board, myPosition);
        singleCalc(row - 2, col - 1, moves, pieceColor, board, myPosition);
        singleCalc(row + 1, col + 2, moves, pieceColor, board, myPosition);
        singleCalc(row + 1, col - 2, moves, pieceColor, board, myPosition);
        singleCalc(row - 1, col + 2, moves, pieceColor, board, myPosition);
        singleCalc(row - 1, col - 2, moves, pieceColor, board, myPosition);

        return moves;

    }

}