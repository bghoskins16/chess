package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import static ui.EscapeSequences.*;

public class DrawChessBoard {

    ChessBoard board;
    ChessGame game;
    ChessPosition highlightPiece;
    boolean highlight = false;
    Collection<ChessPosition> positionsToHighlight;

    public DrawChessBoard() {
        board = new ChessBoard();
    }

    public void printStartingBoard(){
        ChessBoard board = new ChessBoard();
        board.resetBoard();

        setBoard(board);
        drawBoard(true);
        drawBoard(false);
    }

    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }

    public void setHighlightPiece(ChessPosition highlightPiece){
        this.highlightPiece = highlightPiece;
    }

    public void drawBoard(boolean whiteAtBottom) {
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        //When player view is black everything is reversed

        drawEdgeRow(out, whiteAtBottom);
        for (int i = 1; i <= 8; i++) {
            drawBoardRow(out, i, whiteAtBottom);
        }
        drawEdgeRow(out, whiteAtBottom);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
        out.println();
    }

    public void drawEdgeRow(PrintStream out, boolean isReversed) {
        char[] headers = {' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', ' '};

        if (isReversed) {
            for (char header : headers) {
                drawEdgeTile(out, header);
            }
        } else {
            for (int i = headers.length - 1; i >= 0; i--) {
                drawEdgeTile(out, headers[i]);
            }
        }
        out.println();
    }

    public void drawBoardRow(PrintStream out, int row, boolean isReversed) {
        char rowSymbol;
        if (isReversed) {
            rowSymbol = (char) ('9' - row);
        } else {
            rowSymbol = (char) ('0' + row);
        }


        drawEdgeTile(out, rowSymbol);
        for (int col = 1; col <= 8; col++) {
            boolean isTileWhite = (row + col) % 2 == 0;

            ChessPiece piece;
            if (isReversed) {
                piece = board.getPiece(new ChessPosition(9 - row, col));
            } else {
                piece = board.getPiece(new ChessPosition(row, 9 - col));
            }

            boolean isPieceWhite = (piece == null) || (piece.getTeamColor() == ChessGame.TeamColor.WHITE);
            char pieceSymbol = getChessSymbol(piece);

            if (!highlight) {
                drawBoardTile(out, pieceSymbol, isTileWhite, isPieceWhite);
            }
            else{
                // Added logic to highlight valid moves of a specific piece
                ChessPosition currentPosition;
                if (isReversed) {
                    currentPosition = new ChessPosition(9-row, col);
                }else {
                    currentPosition = new ChessPosition(row, 9-col);
                }
                boolean highlightHere = false;
                for (ChessPosition pos: positionsToHighlight){
                    if (currentPosition.equals(pos)){
                        highlightHere = true;
                        break;
                    }
                }

                if (highlightHere){
                    drawHighlightTile(out, pieceSymbol, isPieceWhite);
                } else {
                    drawBoardTile(out, pieceSymbol, isTileWhite, isPieceWhite);
                }

            }
        }
        drawEdgeTile(out, rowSymbol);
        out.println();
    }

    public char getChessSymbol(ChessPiece piece) {
        char symbol = ' ';

        if (piece == null) {
            return symbol;
        }

        switch (piece.getPieceType()) {
            case KING -> symbol = 'K';
            case QUEEN -> symbol = 'Q';
            case ROOK -> symbol = 'R';
            case BISHOP -> symbol = 'B';
            case KNIGHT -> symbol = 'N';
            case PAWN -> symbol = 'P';
        }

        return symbol;
    }

    public void drawEdgeTile(PrintStream out, char symbol) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_DARK_GREY);

        out.print(" " + symbol + " ");

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    public void drawBoardTile(PrintStream out, char symbol, boolean isTileWhite, boolean isPieceWhite) {
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

    public void drawBoardHighlighted(boolean whiteAtBottom) {
        highlight = true;
        Collection<ChessMove> moves = game.validMoves(highlightPiece);

        positionsToHighlight = new ArrayList<>();
        for (ChessMove move: moves){
            positionsToHighlight.add(move.getEndPosition());
        }

        board = game.getBoard();
        drawBoard(whiteAtBottom);

        highlight = false;

    }

    public void drawHighlightTile(PrintStream out, char symbol, boolean isPieceWhite) {
        out.print(SET_BG_COLOR_YELLOW);

        if (isPieceWhite) {
            out.print(SET_TEXT_COLOR_WHITE);
        } else {
            out.print(SET_TEXT_COLOR_BLACK);
        }

        out.print(" " + symbol + " ");
    }
}
