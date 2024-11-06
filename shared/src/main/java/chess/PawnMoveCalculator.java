package chess;

import java.util.Collection;
import java.util.LinkedList;

public class PawnMoveCalculator extends PieceMoveCalculator {

    @Override
    public Collection<ChessMove> pieceMoveCalculator(
            ChessGame.TeamColor pieceColor,
            ChessPiece.PieceType type,
            ChessBoard board,
            ChessPosition myPosition) {
        Collection<ChessMove> moves = new LinkedList<>();

        int startRow = myPosition.getRow();
        int startCol = myPosition.getColumn();

        int endRow;
        ChessPosition endPos;

        if (pieceColor == ChessGame.TeamColor.WHITE) {
            endRow = startRow + 1;
        } else {
            endRow = startRow - 1;
        }

        boolean promotion = (endRow == 1 || endRow == 8);

        // Try up and to the right
        if (startCol > 1) {
            endPos = new ChessPosition(endRow, startCol - 1);
            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    addMoveWithPromotions(promotion, moves, myPosition, endPos);
                }
            }
        }

        // Try up and to the left
        if (startCol < 8) {
            endPos = new ChessPosition(endRow, startCol + 1);
            // if this is not my own piece add to Collection
            if (board.getPiece(endPos) != null) {
                ChessGame.TeamColor color = board.getPiece(endPos).getTeamColor();
                if (color != pieceColor) {
                    addMoveWithPromotions(promotion, moves, myPosition, endPos);
                }
            }
        }

        // Try up
        if (startRow > 1 && startCol > 1) {
            endPos = new ChessPosition(endRow, startCol);
            if (board.getPiece(endPos) == null) {
                addMoveWithPromotions(promotion, moves, myPosition, endPos);

                // check to see if it can move twice
                if (pieceColor == ChessGame.TeamColor.WHITE && startRow == 2) {
                    endPos = new ChessPosition(endRow + 1, startCol);
                    if (board.getPiece(endPos) == null) {
                        moves.add(new ChessMove(myPosition, endPos, null));
                    }
                } else if (pieceColor == ChessGame.TeamColor.BLACK && startRow == 7) {
                    endPos = new ChessPosition(endRow - 1, startCol);
                    if (board.getPiece(endPos) == null) {
                        moves.add(new ChessMove(myPosition, endPos, null));
                    }
                }
            }
        }

        return moves;
    }

    public void addMoveWithPromotions(boolean promotion, Collection<ChessMove> moves, ChessPosition myPosition, ChessPosition endPos){
        if (promotion) {
            moves.add(new ChessMove(myPosition, endPos, ChessPiece.PieceType.QUEEN));
            moves.add(new ChessMove(myPosition, endPos, ChessPiece.PieceType.ROOK));
            moves.add(new ChessMove(myPosition, endPos, ChessPiece.PieceType.BISHOP));
            moves.add(new ChessMove(myPosition, endPos, ChessPiece.PieceType.KNIGHT));
        }
        else {
            moves.add(new ChessMove(myPosition, endPos, null));
        }
    }
}