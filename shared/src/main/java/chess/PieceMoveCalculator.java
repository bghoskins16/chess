package chess;

import java.util.Collection;

public class PieceMoveCalculator {

    public Collection<ChessMove> pieceMoveCalculator(
            ChessGame.TeamColor pieceColor,
            ChessPiece.PieceType type,
            ChessBoard board,
            ChessPosition myPosition) {
        return null;
    }

    // Add possibles moves to an array moves, until it hits and edge or another piece
    // used in queen, bishop  and rook
    // types 1-4 are diagonals
    // types 5-8 are orthogonals
    public void sequenceCalc(
            int type,
            Collection<ChessMove> moves,
            ChessGame.TeamColor pieceColor,
            ChessBoard board,
            ChessPosition myPosition) {

        int endRow = myPosition.getRow();
        int endCol = myPosition.getColumn();

        while (true) {
            // Change the move Location
            switch (type) {
                case 1:
                    endRow--;
                    endCol++;
                    break;
                case 2:
                    endRow++;
                    endCol++;
                    break;
                case 3:
                    endRow--;
                    endCol--;
                    break;
                case 4:
                    endRow++;
                    endCol--;
                    break;
                case 5:
                    endRow--;
                    break;
                case 6:
                    endRow++;
                    break;
                case 7:
                    endCol--;
                    break;
                case 8:
                    endCol++;
                    break;
                default:
                    return;
            }

            // Check if in bounds
            if (endRow < 1 || endRow > 8 || endCol < 1 || endCol > 8) {
                return;
            }

            ChessPosition endPos = new ChessPosition(endRow, endCol);

            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    moves.add(new ChessMove(myPosition, endPos, null));
                }
                break;
            } else {
                moves.add(new ChessMove(myPosition, endPos, null));
            }
        }

    }

    public void singleCalc(
            int endRow,
            int endCol,
            Collection<ChessMove> moves,
            ChessGame.TeamColor pieceColor,
            ChessBoard board,
            ChessPosition myPosition) {

        if (endRow >= 1 && endRow <= 8 && endCol >= 1 && endCol <= 8) {
            ChessPosition endPos = new ChessPosition(endRow, endCol);
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
    }

}
