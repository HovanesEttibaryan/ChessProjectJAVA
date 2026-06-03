import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

// Represents the Knight chess piece, extending from Pieces class
public class Knight extends Pieces {
	String color; // Color of the knight (white or black)

	// Constructor to initialize the knight with a specified color
	public Knight(String color) {
		this.color = color;
	}

	// Returns the ImageIcon representing the knight piece
	ImageIcon getPieceIcon() {
		ImageIcon icon = new ImageIcon();
		int width = 90;

		// Set the appropriate image based on the knight's color
		if (color.equals("white")) {
			icon = new ImageIcon("whiteknight.png");
		}

		if (color.equals("black")) {
			icon = new ImageIcon("blackknight.png");
		}

		// Scale the image to the specified width with a proportional height
		return new ImageIcon(icon.getImage().getScaledInstance(width, -1, Image.SCALE_SMOOTH));
	}

	// Checks if a move from (fromRow, fromCol) to (toRow, toCol) is a valid move for the knight
	protected boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells,
			List<Move> moveHistory) {
		// Check if the move is an L-shape: two squares in one direction and one square perpendicular
		boolean validMove = ((Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 1)
				|| (Math.abs(toRow - fromRow) == 1 && Math.abs(toCol - fromCol) == 2));

		// Check if the destination position is valid and if there is no piece of the same color
		if (!isValidPosition(toRow, toCol)
				|| (boardCells[toRow][toCol].hasPiece() && color.equals(boardCells[toRow][toCol].getColor()))) {
			validMove = false;
		}

		return validMove;
	}

	// Checks if the knight's move puts the opponent's king in check
	protected boolean ifCheck(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells,
			List<Move> moveHistory) {
		boolean isCheck = false;

		String currentPlayerColor = color; // Assuming you have a method to get the current player's color

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

		// Check if the move is a valid check move
		if (isCheckMove(fromRow, fromCol, toRow, toCol, kingRow, kingCol, boardCells, moveHistory)) {
			isCheck = true;
		}

		return isCheck;
	}

	// Checks if the move is a valid check move for the knight
	protected boolean isCheckMove(int fromRow, int fromCol, int toRow, int toCol, int kingRow, int kingCol,
			BoardCell[][] boardCells, List<Move> moveHistory) {
		// Check if the move is along an L-shape
		boolean validMove = (Math.abs(toRow - kingRow) == 2 && Math.abs(toCol - kingCol) == 1)
				|| (Math.abs(toRow - kingRow) == 1 && Math.abs(toCol - kingCol) == 2);

		// Additional checks can be added here if needed

		return validMove;
	}

	// Getter method to retrieve the color of the knight
	protected String getColor() {
		return color;
	}

	// Getter method to retrieve the hasMovedDouble variable
	@Override
	protected int gethasMovedDouble() {
		return 0; // Knights don't have the "hasMovedDouble" property
	}

	// Check if the knight's move results in the promotion of a pawn (not applicable for knights)
	@Override
	protected boolean ifPromotion(int selectedRow, int selectedCol, int row, int col, BoardCell[][] boardCells) {
		return false; // Knights don't lead to pawn promotion
	}

	// Retrieves a list of valid moves for the knight from the selected position
	@Override
	List<Move> getValidMoves(int selectedRow, int selectedCol, BoardCell[][] boardCells) {
		List<Move> validMoves = new ArrayList<>();
		for (int r = 0; r < boardCells.length; r++) {
			for (int c = 0; c < boardCells[0].length; c++) {
				if (isValidMove(selectedRow, selectedCol, r, c, boardCells, null)) {
					validMoves.add(new Move(selectedRow, selectedCol, r, c,
							boardCells[selectedRow][selectedCol].getPiece()));
				}
			}
		}
		return validMoves;
	}

	// Checks if the specified position is within the valid range of the chessboard
	private boolean isValidPosition(int row, int col) {
		return row >= 0 && row < 8 && col >= 0 && col < 8;
	}

	// Checks if the move is a capture move for the knight
	protected boolean ifCaptureMove(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells,
			List<Move> moveHistory) {
		boolean isCaptureMove = false;

		// Check if the move is valid
		if (isValidMove(fromRow, fromCol, toRow, toCol, boardCells, moveHistory)) {
			// Check if the destination cell has an opponent's piece
			if (boardCells[toRow][toCol].hasPiece()
					&& !boardCells[toRow][toCol].getColor().equals(boardCells[fromRow][fromCol].getColor())) {
				isCaptureMove = true;
			}
		}

		return isCaptureMove;
	}

	// Checks if the previous move resulted in a check for the opponent
	protected boolean ifWasCheck(int fromRow, int fromCol, int toRow, int toCol, int moveRow, int moveCol,
			BoardCell[][] boardCells, List<Move> moveHistory) {
		boolean isCheck = false;

		String playerColor = color; // Assuming you have a method to get the current player's color

		// Find the position of the opponent's king
		int kingRow = -1;
		int kingCol = -1;
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				BoardCell cell = boardCells[r][c];
				if (cell.hasPiece() && cell.getPiece() instanceof King && !cell.getColor().equals(playerColor)) {
					kingRow = r;
					kingCol = c;
					break;
				}
			}
			if (kingRow != -1) {
				break;
			}
		}

		// Check if the move results in a check for the opponent
		if (isCheckMove(fromRow, fromCol, toRow, toCol, kingRow, kingCol, boardCells, moveHistory)) {
			isCheck = true;
		}

		return isCheck;
	}

	// Placeholder methods (not implemented)
	@Override
	protected boolean hasValidMoves(BoardCell[][] boardCells, List<Move> moveHistory) {
		return false; // TODO: Implement method
	}

	@Override
	protected boolean KingSideCastle(int selectedRow, int selectedCol, int row, int col, BoardCell[][] boardCells,
			List<Move> moveHistory) {
		return false; // TODO: Implement method
	}

	@Override
	protected boolean QueenSideCastle(int selectedRow, int selectedCol, int row, int col, BoardCell[][] boardCells,
			List<Move> moveHistory) {
		return false; // TODO: Implement method
	}
}
