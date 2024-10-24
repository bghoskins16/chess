package chess;

import java.util.Collection;
import java.util.LinkedList;

public class KnightMoveCalculator extends PieceMoveCalculator {

    @Override
    public Collection<ChessMove> pieceMoveCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type, ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new LinkedList<>();

        int startRow = myPosition.getRow();
        int startCol = myPosition.getColumn();

        int endRow;
        int endCol;
        ChessPosition endPos;

        // case 1
        endRow = startRow + 1;
        endCol = startCol + 2;
        if (endRow >= 1 && endRow <= 8 && endCol >= 1 && endCol <= 8) {
            endPos = new ChessPosition(endRow, endCol);
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

        // case 2
        endRow = startRow + 1;
        endCol = startCol - 2;
        if (endRow >= 1 && endRow <= 8 && endCol >= 1 && endCol <= 8) {
            endPos = new ChessPosition(endRow, endCol);
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

        // case 3
        endRow = startRow - 1;
        endCol = startCol + 2;
        if (endRow >= 1 && endRow <= 8 && endCol >= 1 && endCol <= 8) {
            endPos = new ChessPosition(endRow, endCol);
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

        // case 4
        endRow = startRow - 1;
        endCol = startCol - 2;
        if (endRow >= 1 && endRow <= 8 && endCol >= 1 && endCol <= 8) {
            endPos = new ChessPosition(endRow, endCol);
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

        // case 5
        endRow = startRow + 2;
        endCol = startCol + 1;
        if (endRow >= 1 && endRow <= 8 && endCol >= 1 && endCol <= 8) {
            endPos = new ChessPosition(endRow, endCol);
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

        // case 6
        endRow = startRow + 2;
        endCol = startCol - 1;
        if (endRow >= 1 && endRow <= 8 && endCol >= 1 && endCol <= 8) {
            endPos = new ChessPosition(endRow, endCol);
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

        // case 7
        endRow = startRow - 2;
        endCol = startCol + 1;
        if (endRow >= 1 && endRow <= 8 && endCol >= 1 && endCol <= 8) {
            endPos = new ChessPosition(endRow, endCol);
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

        // case 8
        endRow = startRow - 2;
        endCol = startCol - 1;
        if (endRow >= 1 && endRow <= 8 && endCol >= 1 && endCol <= 8) {
            endPos = new ChessPosition(endRow, endCol);
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