import java.util.ArrayList;
import java.util.List;

public class Move {
    private final int startRow;
    private final int startCol;
    private final int endRow;
    private final int endCol;
    private final Pieces movedPiece;
    private boolean ifCheck = false;

    private static List<Move> moveHistory = new ArrayList<>();

    public Move(int startRow, int startCol, int endRow, int endCol, Pieces movedPiece, boolean ifCheck) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.movedPiece = movedPiece;
        this.ifCheck = ifCheck;


        // Add the current move to the history
        moveHistory.add(this);
    }

    public Move(int selectedRow, int selectedCol, int selectedRow2, int col, Pieces piece) {
    	startRow = selectedRow;
        startCol = selectedCol;
        endRow = selectedRow2;
        endCol = col;
        movedPiece = piece;
	}

	// Getter methods for the move details
    
    //Getter method to see which row the piece moved from
    public int getStartRow() {
        return startRow;
    }

    //Getter method to see which col the piece moved from
    public int getStartCol() {
        return startCol;
    }

    //Getter method to see which row the piece ended at
    public int getEndRow() {
        return endRow;
    }

    
    //Getter method to see which col the piece ended at
    public int getEndCol() {
        return endCol;
    }

    //Getter method to see which piece was moved
    public Pieces getMovedPiece() {
        return movedPiece;
    }
    
    
    //Getter method to see if move was check
    public boolean getIfCheck() {
    	return ifCheck;
    }
    
    
    // Get the last move from the history
    public static Move getLastMove() {
        if (!moveHistory.isEmpty()) {
            return moveHistory.get(moveHistory.size() - 1);
        } else {
            return null; // No moves in history
        }
    }

    // Override toString for a readable representation
    @Override
    public String toString() {
        return "Move: " + movedPiece.getClass().getSimpleName() +
                " from (" + startRow + ", " + startCol + ") to (" + endRow + ", " + endCol + ")";
    }
}
