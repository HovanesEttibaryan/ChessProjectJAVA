import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

// Pawn class represents the pawn chess piece, extending from Pieces class
public class Pawn extends Pieces {
    private String color; // Color of the pawn (white or black)
    private int hasMovedDouble = 0; // Indicates if the pawn has moved two steps forward in the previous move
    
    // Constructor to initialize the pawn with a specified color
    public Pawn(String color) {
        this.color = color;
    }

    // Returns the ImageIcon representing the pawn piece
    ImageIcon getPieceIcon() {
        ImageIcon icon = new ImageIcon();
        int width = 90;

        // Set the appropriate image based on the pawn's color
        if (color.equals("white")) {
            icon = new ImageIcon("whitepawn.png");
        }

        if (color.equals("black")) {
            icon = new ImageIcon("blackpawn.png");
        }

        // Scale the image to the specified width with a proportional height
        return new ImageIcon(icon.getImage().getScaledInstance(width, -1, Image.SCALE_SMOOTH));
    }

    // Getter method to retrieve the color of the pawn
    protected String getColor() {
        return color;
    }

    // Checks if a move from (fromRow, fromCol) to (toRow, toCol) is a valid move
    protected boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells, List<Move> moveHistory) {
        boolean validMove = false;

        // Check valid moves for white pawns
        if (color.equals("white")) {
            // Check if the move is one step forward or two steps forward from the starting position and on the same column
            if ((((toRow == fromRow - 1 && toCol == fromCol) || (fromRow == 6 && toRow == 4 && toCol == fromCol))
                    && isPathClear(fromRow, fromCol, toRow, toCol, boardCells)) || isCaptureMove(fromRow, fromCol, toRow, toCol, boardCells, moveHistory)
                    || isEnPassant(fromRow, fromCol, toRow, toCol, boardCells, moveHistory) || ifPromotion(fromRow, fromCol, toRow, toCol, boardCells) ||
                    ifCheck(fromRow, fromCol, toRow, toCol, boardCells, moveHistory)) {
                validMove = true;
            }

            // Update hasMovedDouble if the pawn moves two steps forward
            if (fromRow == 6 && toRow == 4 && toCol == fromCol) {
                hasMovedDouble = 1;
            } else {
                hasMovedDouble = 0;
            }

        } else if (color.equals("black")) {
            // Check valid moves for black pawns
            if ((((toRow == fromRow + 1 && toCol == fromCol) || (fromRow == 1 && toRow == 3 && toCol == fromCol))
                    && isPathClear(fromRow, fromCol, toRow, toCol, boardCells)) || isCaptureMove(fromRow, fromCol, toRow, toCol, boardCells, moveHistory)
                    || isEnPassant(fromRow, fromCol, toRow, toCol, boardCells, moveHistory) || ifPromotion(fromRow, fromCol, toRow, toCol, boardCells) ||
                    ifCheck(fromRow, fromCol, toRow, toCol, boardCells, moveHistory)) {
                validMove = true;
            }

            // Update hasMovedDouble if the pawn moves two steps forward
            if (fromRow == 1 && toRow == 3 && toCol == fromCol) {
                hasMovedDouble = 1;
            } else {
                hasMovedDouble = 0;
            }
        }

        // Check if the destination cell has a King piece
        if((boardCells[toRow][toCol].hasPiece() && boardCells[toRow][toCol].getPiece() instanceof King)) {
	    	validMove = false;
	    }

        return validMove;
    }

    // Checks if the move is a capture move for the pawn
    boolean isCaptureMove(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells, List<Move> moveHistory) {
        boolean isCaptureMove = false;

        // Check for capture moves for white pawns
        if (toRow >=0 && toRow <=7 && toCol >=0 && toCol<=7 && color.equals("white") && fromRow - toRow == 1 && (fromCol - toCol == 1 || fromCol - toCol == -1)
                && boardCells[toRow][toCol].hasPiece() && boardCells[toRow][toCol].getColor().equals("black")
                && !(boardCells[toRow][toCol].getPiece() instanceof King)) {
            isCaptureMove = true;
        }

        // Check for capture moves for black pawns
        if (toRow >=0 && toRow <=7 && toCol >=0 && toCol<=7 && color.equals("black") && fromRow - toRow == -1 && (fromCol - toCol == 1 || fromCol - toCol == -1)
                && boardCells[toRow][toCol].hasPiece() && boardCells[toRow][toCol].getColor().equals("white")
                && !(boardCells[toRow][toCol].getPiece() instanceof King)) {
            isCaptureMove = true;
        }

        return isCaptureMove;
    }

    // Checks if the path from (fromRow, fromCol) to (toRow, toCol) is clear of other pieces
    private boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells) {
        // Check for white pawns
        if (color.equals("white")) {
            if (boardCells[toRow][toCol].hasPiece() || boardCells[fromRow - 1][fromCol].hasPiece()) {
                return false;
            }
        }

        // Check for black pawns
        if (color.equals("black")) {
            if (boardCells[toRow][toCol].hasPiece() || boardCells[fromRow + 1][fromCol].hasPiece()) {
                return false;
            }
        }

        return true;
    }

    // Checks if the move is an en passant move for the pawn
    boolean isEnPassant(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells, List<Move> moveHistory) {
        boolean isEnPassant = false;
        Move lastMove = Move.getLastMove();

        // Check for en passant moves for white pawns
        if (color.equals("white") && fromRow == 3) {
            if (boardCells[fromRow][toCol].getPiece() instanceof Pawn
                    && boardCells[fromRow][toCol].getPiece().gethasMovedDouble() == 1
                    && lastMove.getEndRow() == fromRow && lastMove.getEndCol() == toCol) {
                isEnPassant = true;
            }
        }

        // Check for en passant moves for black pawns
        if (color.equals("black") && fromRow == 4) {
            if (boardCells[fromRow][toCol].getPiece() instanceof Pawn
                    && boardCells[fromRow][toCol].getPiece().gethasMovedDouble() == 1
                    && lastMove.getEndRow() == fromRow && lastMove.getEndCol() == toCol) {
                isEnPassant = true;
            }
        }

        return isEnPassant;
    }

    // Returns the piece involved in an en passant move
    Pieces enPassantPiece(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells) {
        Pieces enPassantPiece = null;

        // Check for white pawns
        if (color.equals("white") && fromRow - 2 == toRow && fromCol == toCol) {
            enPassantPiece = boardCells[fromRow][toCol].getPiece();
        }

        // Check for black pawns
        if (color.equals("black") && fromRow + 2 == toRow && fromCol == toCol) {
            enPassantPiece = boardCells[fromRow][toCol].getPiece();
        }

        return enPassantPiece;
    }

    // Getter method to retrieve the hasMovedDouble variable
    protected int gethasMovedDouble() {
        return hasMovedDouble;
    }

    // Checks if the pawn has reached the promotion row
    protected boolean ifPromotion(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells) {
        boolean ifPromotion = false;

        // Check for promotion of white pawns
        if (color.equals("white") && fromRow == 1 && toRow == 0) {
            ifPromotion = true;
        }

        // Check for promotion of black pawns
        if (color.equals("black") && fromRow == 6 && toRow == 7) {
            ifPromotion = true;
        }

        return ifPromotion;
    }

    // Checks if the pawn's move puts the opponent's king in check
    protected boolean ifCheck(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells, List<Move> moveHistory) {
        boolean isCheck = false;
        String currentPlayerColor = color;

        // Find the position of the opponent's king
        int kingRow = -1;
        int kingCol = -1;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                BoardCell cell = boardCells[r][c];
                if (cell.hasPiece() && cell.getPiece() instanceof King && !cell.getColor().equals(currentPlayerColor)) {
                    kingRow = r;
                    kingCol = c;
                    break;
                }
            }
            if (kingRow != -1) {
                break;
            }
        }

        // Check if the move is a capture move on the opponent's king
        if (isCaptureMove(toRow, toCol, kingRow, kingCol, boardCells, moveHistory)) {
            isCheck = true;
        }

        return isCheck;
    }

    // TODO: Implement the remaining methods based on the Pieces class

    List<Move> getValidMoves(int selectedRow, int selectedCol, BoardCell[][] boardCells) {
        List<Move> validMoves = new ArrayList<>();
        for(int r = 0; r<boardCells.length; r++) {
        	for(int c = 0; c<boardCells[0].length; c++) {
        		if(isValidMove(selectedRow, selectedCol, r, c, boardCells, null)) {
        			validMoves.add(new Move(selectedRow, selectedCol, r, c, boardCells[selectedRow][selectedCol].getPiece()));
        		}
        	}
        }
        return validMoves;
    }

    protected boolean ifWasCheck(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells, List<Move> moveHistory) {
        // TODO: Implement method
        return false;
    }

    @Override
    protected boolean ifWasCheck(int startRow, int startCol, int endRow, int endCol, int row, int col,
            BoardCell[][] boardCells, List<Move> moveHistory) {
        // TODO: Implement method
        return false;
    }

    @Override
    protected boolean hasValidMoves(BoardCell[][] boardCells, List<Move> moveHistory) {
        // TODO: Implement method
        return false;
    }

    @Override
    protected boolean KingSideCastle(int selectedRow, int selectedCol, int row, int col, BoardCell[][] boardCells,
            List<Move> moveHistory) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean QueenSideCastle(int selectedRow, int selectedCol, int row, int col, BoardCell[][] boardCells,
            List<Move> moveHistory) {
        // TODO Auto-generated method stub
        return false;
    }
}
