package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */

public class ChessBoard {

    private static final int BOARD_SIZE = 8;
//    private static final ChessGame.TeamColor[][] START_COLORS = {
//            {BLACK,BLACK,BLACK,BLACK,BLACK,BLACK,BLACK,BLACK},
//            {BLACK,BLACK,BLACK,BLACK,BLACK,BLACK,BLACK,BLACK},
//            {NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL},
//            {NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL},
//            {NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL},
//            {NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL},
//            {WHITE,WHITE,WHITE,WHITE,WHITE,WHITE,WHITE,WHITE},
//            {WHITE,WHITE,WHITE,WHITE,WHITE,WHITE,WHITE,WHITE}
//    };
//    private static final ChessPiece.PieceType[][] START_PIECES = {
//            {ROOK,KNIGHT,BISHOP,KING,QUEEN,BISHOP,KNIGHT,ROOK},
//            {PAWN,PAWN,PAWN,PAWN,PAWN,PAWN,PAWN,PAWN},
//            {NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL},
//            {NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL},
//            {NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL},
//            {NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL},
//            {PAWN,PAWN,PAWN,PAWN,PAWN,PAWN,PAWN,PAWN},
//            {ROOK,KNIGHT,BISHOP,QUEEN,KING,BISHOP,KNIGHT,ROOK}
//    };


    private ChessPiece[][] board = new ChessPiece[BOARD_SIZE][BOARD_SIZE];

    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()][position.getColumn()];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for (int i = 0; i < BOARD_SIZE; i++){
            for (int j = 0; j < BOARD_SIZE; j++){
                board[i][j] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
            }
        }
    }
}
