package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    TeamColor teamTurn;
    ChessBoard gameBoard;

    public ChessGame() {

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
        for (ChessMove move: allMoves){
            ChessGame gameCopy = this;
            try {
                gameCopy.makeMove(move);
            }
            catch (Exception e){
                continue;
            }

            // Don't add to if it makes the team become in check
            if (gameCopy.isInCheck(TeamColor.WHITE)) continue;
            if (gameCopy.isInCheckmate(TeamColor.WHITE)) continue;

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
        if (move.getPromotionPiece() == null) {
            gameBoard.addPiece(move.getEndPosition(), gameBoard.getPiece(move.getStartPosition()));
        }
        else{
            gameBoard.addPiece(move.getEndPosition(), new ChessPiece(gameBoard.getPiece(move.getStartPosition()).getTeamColor(), move.getPromotionPiece()));
        }
        gameBoard.addPiece(move.getStartPosition(), null);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // Figure out where the king is
        // TODO: Figure this part out
        ChessPosition kingPos = new ChessPosition(0,0);

        //Loop through whole board and see if any move are in the kings spot
        for (int i = 1; i <= 8 - 2; i++){
            for (int j = 1; j <= 8; j++) {
                ChessPosition pos = new ChessPosition(i,j);
                ChessPiece piece = gameBoard.getPiece(pos);

                // Move to next piece if ths piece is empty of same team
                if (piece == null || piece.getTeamColor() == teamColor) continue;

                // Loo through all move and look for the kings position. If found return true.
                Collection<ChessMove> moves = piece.pieceMoves(gameBoard, pos);
                for (ChessMove move: moves){
                    if (move.getEndPosition() == kingPos) {
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
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        throw new RuntimeException("Not implemented");
    }
}
