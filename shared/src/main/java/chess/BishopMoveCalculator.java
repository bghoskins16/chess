package chess;

import java.util.Collection;
import java.util.List;

import java.io.*;
import java.util.*;

public class BishopMoveCalculator extends PieceMoveCalculator {

    @Override
    public Collection<ChessMove> pieceMoveCalculator(
            ChessGame.TeamColor pieceColor,
            ChessPiece.PieceType type,
            ChessBoard board,
            ChessPosition myPosition) {

        Collection<ChessMove> moves = new LinkedList<>();

        for (int i = 1; i <= 4; i++) {
            sequenceCalc(i, moves, pieceColor, board, myPosition);
        }

        return moves;
    }

}