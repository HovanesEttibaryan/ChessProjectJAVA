import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;

// Abstract class representing chess pieces
public abstract class Pieces {

    // Method to scale an ImageIcon to a specified width and height
    protected ImageIcon getScaledImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // Abstract method to retrieve the color of the chess piece
    protected abstract String getColor();

    // Abstract method to check if the piece has moved two squares forward
    int hasMoved2Squares(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells) {
        return 0;
        // TODO Auto-generated method stub
    }

    // Abstract method to check if the move is an en passant move
    boolean isEnPassant(int selectedRow, int selectedCol, int row, int col, BoardCell[][] boardCells, List<Move> moveHistory) {
        // TODO: Implement method
        return false;
    }

    // Abstract method to get a list of valid moves for the chess piece
    abstract List<Move> getValidMoves(int selectedRow, int selectedCol, BoardCell[][] boardCells);

    // Abstract method to get the hasMovedDouble variable
    protected abstract int gethasMovedDouble();

    // Abstract method to check if the move leads to pawn promotion
    protected abstract boolean ifPromotion(int selectedRow, int selectedCol, int row, int col, BoardCell[][] boardCells);

    // Abstract method to check if the move puts the opponent's king in check
    protected abstract boolean ifCheck(int selectedRow, int selectedCol, int row, int col, BoardCell[][] boardCells, List<Move> moveHistory);

    // Abstract method to check if the move is valid
    protected abstract boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells, List<Move> moveHistory);

    // Abstract method to check if the piece was the cause of a check
    protected abstract boolean ifWasCheck(int startRow, int startCol, int endRow, int endCol, int row, int col,
            BoardCell[][] boardCells, List<Move> moveHistory);

    // Abstract method to check if the piece has valid moves on the board
    protected abstract boolean hasValidMoves(BoardCell[][] boardCells, List<Move> moveHistory);

    // Abstract method to check if the piece can perform a king-side castle
    protected abstract boolean KingSideCastle(int selectedRow, int selectedCol, int row, int col,
            BoardCell[][] boardCells, List<Move> moveHistory);

    // Abstract method to check if the piece can perform a queen-side castle
    protected abstract boolean QueenSideCastle(int selectedRow, int selectedCol, int row, int col,
            BoardCell[][] boardCells, List<Move> moveHistory);
}
