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
        while (endRow > 1 && endCol < 8) {
            // Change the move Location
            endRow--;
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

        // Try down and to the right
        endRow = startRow;
        endCol = startCol;
        while (endRow < 8 && endCol < 8) {
            // Change the move Location
            endRow++;
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

        // Try up and to the left
        endRow = startRow;
        endCol = startCol;
        while (endRow > 1 && endCol > 1) {
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
            else {
                moves.add(new ChessMove(myPosition, endPos, null));
            }
        }

        // Try down and to the left
        endRow = startRow;
        endCol = startCol;
        while (endRow < 8 && endCol > 1) {
            // Change the move Location
            endRow++;
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

        return moves;
    }

}