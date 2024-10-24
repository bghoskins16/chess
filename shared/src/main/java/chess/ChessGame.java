package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame implements Cloneable {

    TeamColor teamTurn;
    ChessBoard gameBoard;


    public ChessGame() {
        gameBoard = new ChessBoard();
        gameBoard.resetBoard();
        teamTurn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = gameBoard.getPiece(startPosition);
        if (piece == null) return null;
        Collection<ChessMove> allMoves = piece.pieceMoves(gameBoard, startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();
        for (ChessMove move : allMoves) {
            try {
                // Clone game and board
                ChessGame gameCopy = (ChessGame) clone();
                ChessBoard boardCopy = gameCopy.getBoard();

                // Try making the move
                if (move.getPromotionPiece() == null) {
                    boardCopy.addPiece(move.getEndPosition(), piece);
                } else {
                    boardCopy.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), move.getPromotionPiece()));
                }
                boardCopy.addPiece(move.getStartPosition(), null);
                gameCopy.setBoard(boardCopy);

                // Don't add to if it makes the team become in check
                if (gameCopy.isInCheck(piece.getTeamColor())) continue;
            } catch (Exception e) {
                continue;
            }

            // Otherwise add it to the valid list
            validMoves.add(move);
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //TODO: Add invalid move handling
        //Check to see if this is a valid move (color and location)
        ChessPiece piece = gameBoard.getPiece(move.getStartPosition());
        if (piece == null) {
            // No piece at start location
            throw new InvalidMoveException();
        }
        if (piece.getTeamColor() != teamTurn) {
            // It's not your turn
            throw new InvalidMoveException();
        }
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        boolean moveFound = false;
        for (ChessMove validMove : validMoves) {
            if (move.equals(validMove)) {
                moveFound = true;
                break;
            }
        }
        if (!moveFound) {
            // Not a valid move
            throw new InvalidMoveException();
        }

        // This is a good move so we can go ahead and make the move
        if (move.getPromotionPiece() == null) {
            gameBoard.addPiece(move.getEndPosition(), piece);
        } else {
            gameBoard.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), move.getPromotionPiece()));
        }
        gameBoard.addPiece(move.getStartPosition(), null);

        // Now we need to change whose turn it is
        if (teamTurn == TeamColor.WHITE) {
            teamTurn = TeamColor.BLACK;
        } else {
            teamTurn = TeamColor.WHITE;
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // Figure out where the king is
        ChessPosition kingPos = null;
        FindKing:
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = gameBoard.getPiece(pos);
                if (piece == null) continue;
                if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                    kingPos = pos;
                    break FindKing;
                }
            }
        }
        if (kingPos == null) {
            return false;
        }

        //Loop through whole board and see if any move are in the kings spot
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = gameBoard.getPiece(pos);

                // Move to next piece if ths piece is empty of same team
                if (piece == null || piece.getTeamColor() == teamColor) continue;

                // Loop through all move and look for the kings position. If found return true.
                Collection<ChessMove> moves = piece.pieceMoves(gameBoard, pos);
                for (ChessMove move : moves) {
                    if (move.getEndPosition().equals(kingPos)) {
                        return true;
                    }
                }
            }
        }

        // If it made it here then king is not in check, return false
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // If it is not in check it isn't in checkmate
        if (!isInCheck(teamColor)) {
            return false;
        }

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = gameBoard.getPiece(pos);

                // Move to next piece if ths piece is empty or on the other team
                if (piece == null || piece.getTeamColor() != teamColor) continue;

                //Check Valid moves. If there is any valid moves then it isn't checkmate
                Collection<ChessMove> moves = validMoves(pos);
                if (!moves.isEmpty()) return false;
            }
        }

        // It will reach here if color is in check and has no valid moves, so return true (Checkmate!)
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        // If it is  in check it isn't a stalemate
        if (isInCheck(teamColor)) {
            return false;
        }

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = gameBoard.getPiece(pos);

                // Move to next piece if ths piece is empty or on the other team
                if (piece == null || piece.getTeamColor() != teamColor) continue;

                //Check Valid moves. If there is any valid moves then it isn't a stalemate
                Collection<ChessMove> moves = validMoves(pos);
                if (!moves.isEmpty()) return false;
            }
        }

        // It will reach here if color is not in check and has no valid moves, so return true (stalemate)
        return true;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        gameBoard = board;
        // Might need to copy one piece at a time
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return gameBoard;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        ChessGame game = (ChessGame) super.clone();
        game.setBoard((ChessBoard) getBoard().clone());
        game.setTeamTurn(getTeamTurn());
        return game;
    }
}
