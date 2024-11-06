package chess;

import java.util.Collection;
import java.util.LinkedList;

public class QueenMoveCalculator extends PieceMoveCalculator {

    @Override
    public Collection<ChessMove> pieceMoveCalculator(
            ChessGame.TeamColor pieceColor,
            ChessPiece.PieceType type,
            ChessBoard board,
            ChessPosition myPosition) {

        Collection<ChessMove> moves = new LinkedList<>();

        for (int i = 1; i <= 8; i++){
            sequenceCalc(i, moves, pieceColor, board, myPosition);
        }

        return moves;
    }

}