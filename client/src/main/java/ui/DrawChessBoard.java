package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class DrawChessBoard {

    static ChessBoard board;

    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        board.resetBoard();

        setBoard(board);
        drawBoard();
    }

    public static void setBoard(ChessBoard board) {
        DrawChessBoard.board = board;
    }

    public static void drawBoard() {
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        //When player view is black everything is reversed
        boolean reversed = false;

        drawEdgeRow(out, reversed);
        for (int i = 1; i <= 8; i++) {
            drawBoardRow(out, i, reversed);
        }
        drawEdgeRow(out, reversed);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    public static void drawEdgeRow(PrintStream out, boolean isReversed) {
        char[] headers = {' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', ' '};
        for (char header : headers) {
            drawEdgeTile(out, header);
        }
        out.println();
    }

    public static void drawBoardRow(PrintStream out, int row, boolean isReversed) {
        drawEdgeTile(out, (char) ('0' + row));
        for (int col = 1; col <= 8; col++) {
            boolean isTileWhite = (row + col) % 2 == 0;
            boolean isPieceWhite = true;
            char pieceSymbol = ' ';

            ChessPiece piece = board.getPiece(new ChessPosition(row, col));
            if (piece != null) {
                isPieceWhite = (piece.getTeamColor() == ChessGame.TeamColor.WHITE);
                switch (piece.getPieceType()) {
                    case KING -> pieceSymbol = 'K';
                    case QUEEN -> pieceSymbol = 'Q';
                    case ROOK -> pieceSymbol = 'R';
                    case BISHOP -> pieceSymbol = 'B';
                    case KNIGHT -> pieceSymbol = 'N';
                    case PAWN -> pieceSymbol = 'P';
                }
            }

            drawBoardTile(out, pieceSymbol, isTileWhite, isPieceWhite);
        }
        drawEdgeTile(out, (char) ('0' + row));
        out.println();
    }

    public static void drawEdgeTile(PrintStream out, char symbol) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_DARK_GREY);

        out.print(" " + symbol + " ");

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    public static void drawBoardTile(PrintStream out, char symbol, boolean isTileWhite, boolean isPieceWhite) {
        if (isTileWhite) {
            out.print(SET_BG_COLOR_LIGHT_GREY);
        } else {
            out.print(SET_BG_COLOR_DARK_GREY);
        }
        if (isPieceWhite) {
            out.print(SET_TEXT_COLOR_WHITE);
        } else {
            out.print(SET_TEXT_COLOR_BLACK);
        }

        out.print(" " + symbol + " ");
    }


}
