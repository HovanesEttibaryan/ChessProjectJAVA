import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

// Represents the Bishop chess piece, extending from Pieces class
public class Bishop extends Pieces {
	String color; // Color of the bishop (white or black)

	// Constructor to initialize the bishop with a specified color
	public Bishop(String color) {
		this.color = color;
	}

	// Returns the ImageIcon representing the bishop piece
	ImageIcon getPieceIcon() {
		ImageIcon icon = new ImageIcon();
		int width = 90;

		// Set the appropriate image based on the bishop's color
		if (color.equals("white")) {
			icon = new ImageIcon("whitebishop.png");
		}

		if (color.equals("black")) {
			icon = new ImageIcon("blackbishop.png");
		}

		// Scale the image to the specified width with a proportional height
		return new ImageIcon(icon.getImage().getScaledInstance(width, -1, Image.SCALE_SMOOTH));
	}

	// Getter method to retrieve the color of the bishop
	protected String getColor() {
		return color;
	}

	// Checks if a move from (fromRow, fromCol) to (toRow, toCol) is a valid move for the bishop
	protected boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells,
			List<Move> moveHistory) {
		// Check if the move is along a diagonal
		boolean validMove = (fromCol - toCol == fromRow - toRow) || (fromCol - toCol == toRow - fromRow)
				|| (toCol - fromCol == fromRow - toRow) || (toCol - fromCol == toRow - fromRow);

		// Check if the path is clear for the bishop move
		if (!isPathClear(fromRow, fromCol, toRow, toCol, boardCells)) {
			validMove = false;
		}

		// Check if the destination cell has a piece of the same color
		if (boardCells[toRow][toCol].hasPiece() && color.equals(boardCells[toRow][toCol].getColor())) {
			validMove = false;
		}

		return validMove;
	}

	// Checks if the path is clear for the bishop move
	private boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells) {
		// Check if the move is along a diagonal
		if (Math.abs(toRow - fromRow) != Math.abs(toCol - fromCol)) {
			// Not a diagonal move, so the path is already clear
			return false;
		}

		// Determine the direction of the diagonal
		int rowIncrement, colIncrement;

		if (toRow > fromRow) {
			rowIncrement = 1; // Move down
		} else {
			rowIncrement = -1; // Move up
		}

		if (toCol > fromCol) {
			colIncrement = 1; // Move right
		} else {
			colIncrement = -1; // Move left
		}

		int currentRow = fromRow + rowIncrement;
		int currentCol = fromCol + colIncrement;

		// Iterate over the diagonal path to check for obstructions
		while (currentRow >= 0 && currentRow <= 7 && currentRow != toRow && currentCol >= 0 && currentCol <= 7
				&& currentCol != toCol) {
			if (boardCells[currentRow][currentCol].hasPiece()) {
				// There is a piece in the way
				return false;
			}

			// Move to the next square along the diagonal
			currentRow += rowIncrement;
			currentCol += colIncrement;
		}

		// The path is clear
		return true;
	}

	// Checks if the move puts the opponent's king in check
	protected boolean ifCheck(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells,
			List<Move> moveHistory) {
		boolean isCheck = false;

		String currentPlayerColor = color;

		// Find the position of the opponent's king
		int kingRow = -1;
		int kingCol = -1;
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				BoardCell cell = boardCells[r][c];
				if (cell.hasPiece() && cell.getPiece() instanceof King
						&& !cell.getColor().equals(currentPlayerColor)) {
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

	// Checks if the move is a valid check move for the bishop
	protected boolean isCheckMove(int fromRow, int fromCol, int toRow, int toCol, int kingRow, int kingCol,
			BoardCell[][] boardCells, List<Move> moveHistory) {
		// Check if the move is along a diagonal
		boolean validMove = (kingCol - toCol == kingRow - toRow) || (kingCol - toCol == toRow - kingRow)
				|| (toCol - kingCol == kingRow - toRow) || (toCol - kingCol == toRow - kingRow);

		// Check if the path is clear for the bishop move
		if (validMove) {
			if (!isPathClear(toRow, toCol, kingRow, kingCol, boardCells)) {
				validMove = false;
			}
		}

		return validMove;
	}

	// Placeholder methods (not implemented)
	@Override
	protected int gethasMovedDouble() {
		return 0; // TODO: Implement method
	}

	@Override
	protected boolean ifPromotion(int selectedRow, int selectedCol, int row, int col, BoardCell[][] boardCells) {
		return false; // TODO: Implement method
	}

	// Retrieves a list of valid moves for the bishop from a selected position
	List<Move> getValidMoves(int selectedRow, int selectedCol, BoardCell[][] boardCells) {
		List<Move> validMoves = new ArrayList<>();

		// Iterate over diagonals
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (isValidMove(selectedRow, selectedCol, row, col, boardCells, null)) {
					validMoves.add(new Move(selectedRow, selectedCol, row, col,
							boardCells[selectedRow][selectedCol].getPiece()));
				}
			}
		}

		return validMoves;
	}

	// Checks if the move is a capture move (capturing an opponent's piece)
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

	// Checks if the move was a check move in the previous turn
	protected boolean ifWasCheck(int fromRow, int fromCol, int toRow, int toCol, int moveRow, int moveCol,
			BoardCell[][] boardCells, List<Move> moveHistory) {
		boolean isCheck = false;

		String playerColor = moveHistory.get(moveHistory.size() - 1).getMovedPiece().getColor();

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

		// Check if the move is a valid check move
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
