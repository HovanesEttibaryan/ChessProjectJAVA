import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

// Represents the King piece in a chess game
public class King extends Pieces {

    // Instance variables
    private String color;
    private boolean hasMoved = false;

    // Constructor
    public King(String color) {
        this.color = color;
    }

    // Get the ImageIcon for the King piece based on its color
    ImageIcon getPieceIcon() {
        ImageIcon icon = new ImageIcon();
        int width = 90;

        if (color.equals("white")) {
            icon = new ImageIcon("whiteking.png");
        }

        if (color.equals("black")) {
            icon = new ImageIcon("blackking.png");
        }

        return new ImageIcon(icon.getImage().getScaledInstance(width, -1, Image.SCALE_SMOOTH));
    }

    // Get the color of the King piece
    protected String getColor() {
        return color;
    }

    // Check if the King has moved
    boolean getHasMoved() {
        return hasMoved;
    }

    // Check if a move is valid for the King piece
    protected boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells,
            List<Move> moveHistory) {

        // Check if the move is along the same row or column
        boolean validMove = (fromCol - toCol == 1 || fromCol - toCol == -1) || (fromRow - toRow == 1 || fromRow - toRow == -1)
                || KingSideCastle(fromRow, fromCol, toRow, toCol, boardCells, moveHistory)
                || QueenSideCastle(fromRow, fromCol, toRow, toCol, boardCells, moveHistory);

        // Check if the path is clear
        if (validMove) {
            if (!isPathClear(fromRow, fromCol, toRow, toCol, boardCells)) {
                validMove = false;
            }
        }

        // Check if the destination cell has a piece with the same color
        if (boardCells[toRow][toCol].hasPiece() && boardCells[toRow][toCol].getColor().equals(color)) {
            validMove = false;
        }

        // Check if the destination cell has a King piece
        if ((boardCells[toRow][toCol].hasPiece() && boardCells[toRow][toCol].getPiece() instanceof King)) {
            validMove = false;
        }

        // If the move is valid, mark the King as moved
        if (validMove) {
            hasMoved = true;
        }

        return validMove;
    }

    // Check if the path from source to destination is clear
    private static boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells) {
        // Check if the move is one square in any direction
        int rowDifference = Math.abs(toRow - fromRow);
        int colDifference = Math.abs(toCol - fromCol);

        // The move is within the king's range, so the path is clear
        return rowDifference <= 1 && colDifference <= 1;
    }

    // Check if it is safe to castle queenside
    boolean safeToCastleQueen(int fromRow, int fromCol, BoardCell[][] boardCells, List<Move> moveHistory) {
        boolean safe = true;

        if (color.equals("white")) {
            for (int r = 0; r < boardCells.length; r++) {
                for (int c = 0; c < boardCells[0].length; c++) {
                    if (boardCells[r][c].hasPiece() && boardCells[r][c].getColor().equals("black")) {
                        Pieces piece = boardCells[r][c].getPiece();
                        if (piece.isValidMove(r, c, 7, 2, boardCells, moveHistory)
                                || piece.isValidMove(r, c, 7, 3, boardCells, moveHistory)) {
                            safe = false;
                        }
                    }
                }
            }
        }

        if (color.equals("black")) {
            for (int r = 0; r < boardCells.length; r++) {
                for (int c = 0; c < boardCells[0].length; c++) {
                    if (boardCells[r][c].hasPiece() && boardCells[r][c].getColor().equals("white")) {
                        Pieces piece = boardCells[r][c].getPiece();
                        if (piece.isValidMove(r, c, 0, 2, boardCells, moveHistory)
                                || piece.isValidMove(r, c, 0, 3, boardCells, moveHistory)) {
                            safe = false;
                        }
                    }
                }
            }
        }

        return safe;
    }

    // Check if it is safe to castle kingside
    boolean safeToCastleKing(int fromRow, int fromCol, BoardCell[][] boardCells, List<Move> moveHistory) {
        boolean safe = true;

        if (color.equals("white")) {
            for (int r = 0; r < boardCells.length; r++) {
                for (int c = 0; c < boardCells[0].length; c++) {
                    if (boardCells[r][c].hasPiece() && boardCells[r][c].getColor().equals("black")) {
                        Pieces piece = boardCells[r][c].getPiece();
                        if (piece.isValidMove(r, c, 7, 6, boardCells, moveHistory)
                                || piece.isValidMove(r, c, 7, 5, boardCells, moveHistory)) {
                            safe = false;
                        }
                    }
                }
            }
        }

        if (color.equals("black")) {
            for (int r = 0; r < boardCells.length; r++) {
                for (int c = 0; c < boardCells[0].length; c++) {
                    if (boardCells[r][c].hasPiece() && boardCells[r][c].getColor().equals("white")) {
                        Pieces piece = boardCells[r][c].getPiece();
                        if (piece.isValidMove(r, c, 0, 6, boardCells, moveHistory)
                                || piece.isValidMove(r, c, 0, 5, boardCells, moveHistory)) {
                            safe = false;
                        }
                    }
                }
            }
        }

        return safe;
    }

    // Check if kingside castling is possible
    protected boolean KingSideCastle(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells,
            List<Move> moveHistory) {
        boolean kingside = false;

        if (color.equals("white") && hasMoved == false && !boardCells[7][6].hasPiece() && !boardCells[7][5].hasPiece()
                && toRow == 7 && toCol == 6 && safeToCastleKing(fromRow, fromCol, boardCells, moveHistory)) {
            kingside = true;
        }

        if (color.equals("black") && hasMoved == false && !boardCells[0][6].hasPiece() && !boardCells[0][5].hasPiece()
                && toRow == 0 && toCol == 6 && safeToCastleKing(fromRow, fromCol, boardCells, moveHistory)) {
            kingside = true;
        }

        return kingside;
    }

    // Check if queenside castling is possible
    protected boolean QueenSideCastle(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells,
            List<Move> moveHistory) {
        boolean queenside = false;

        if (color.equals("white") && hasMoved == false && !boardCells[7][1].hasPiece() && !boardCells[7][2].hasPiece()
                && !boardCells[7][3].hasPiece() && toRow == 7 && toCol == 2
                && safeToCastleQueen(fromRow, fromCol, boardCells, moveHistory)) {
            queenside = true;
        }

        if (color.equals("black") && hasMoved == false && !boardCells[0][1].hasPiece() && !boardCells[0][2].hasPiece()
                && !boardCells[0][3].hasPiece() && toRow == 0 && toCol == 2
                && safeToCastleQueen(fromRow, fromCol, boardCells, moveHistory)) {
            queenside = true;
        }

        return queenside;
    }

    // Check if the King has valid moves on the board
    protected boolean hasValidMoves(BoardCell[][] boardCells, List<Move> moveHistory) {
        for (int r = 0; r < boardCells.length; r++) {
            for (int c = 0; c < boardCells[0].length; c++) {
                if (boardCells[r][c].hasPiece() && boardCells[r][c].getPiece() instanceof King
                        && boardCells[r][c].getColor().equals(color)) {
                    King king = (King) boardCells[r][c].getPiece();

                    // Check all possible moves for the king
                    for (int newRow = r - 1; newRow <= r + 1; newRow++) {
                        for (int newCol = c - 1; newCol <= c + 1; newCol++) {
                            if (king.isValidMove(r, c, newRow, newCol, boardCells, moveHistory)) {
                                return true; // King has at least one valid move
                            }
                        }
                    }

                    // Check for castling moves
                    if (king.KingSideCastle(r, c, r, c + 2, boardCells, moveHistory)
                            || king.QueenSideCastle(r, c, r, c - 2, boardCells, moveHistory)) {
                        return true; // King can castle
                    }
                }
            }
        }

        return false; // No valid moves for the king
    }

    // Get a list of valid moves for the King piece
    List<Move> getValidMoves(int selectedRow, int selectedCol, BoardCell[][] boardCells) {
        List<Move> validMoves = new ArrayList<>();
        for (int r = 0; r < boardCells.length; r++) {
            for (int c = 0; c < boardCells[0].length; c++) {
                if (isValidMove(selectedRow, selectedCol, r, c, boardCells, null)) {
                    validMoves.add(new Move(selectedRow, selectedCol, r, c, boardCells[selectedRow][selectedCol].getPiece()));
                }
            }
        }
        return validMoves;
    }
    
    // Placeholder method - not implemented for King
    @Override
    protected int gethasMovedDouble() {
        return 0;
    }

    // Placeholder method - not implemented for King
    @Override
    protected boolean ifPromotion(int selectedRow, int selectedCol, int row, int col, BoardCell[][] boardCells) {
        return false;
    }

    // Placeholder method - not implemented for King
    @Override
    protected boolean ifCheck(int selectedRow, int selectedCol, int row, int col, BoardCell[][] boardCells,
            List<Move> moveHistory) {
        return false;
    }

    // Placeholder method - not implemented for King
    @Override
    protected boolean ifWasCheck(int startRow, int startCol, int endRow, int endCol, int row, int col,
            BoardCell[][] boardCells, List<Move> moveHistory) {
        return false;
    }
}
