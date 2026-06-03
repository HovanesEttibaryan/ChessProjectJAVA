import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

// Represents the Queen chess piece, extending from Pieces class
public class Queen extends Pieces {
	String color; // Color of the queen (white or black)

	// Constructor to initialize the queen with a specified color
	public Queen(String color) {
		this.color = color;
	}

	// Returns the ImageIcon representing the queen piece
	ImageIcon getPieceIcon() {
		ImageIcon icon = new ImageIcon();
		int width = 90;

		// Set the appropriate image based on the queen's color
		if (color.equals("white")) {
			icon = new ImageIcon("whitequeen.png");
		}

		if (color.equals("black")) {
			icon = new ImageIcon("blackqueen.png");
		}

		// Scale the image to the specified width with a proportional height
		return new ImageIcon(icon.getImage().getScaledInstance(width, -1, Image.SCALE_SMOOTH));
	}

	// Getter method to retrieve the color of the queen
	protected String getColor() {
		return color;
	}

	// Checks if a move from (fromRow, fromCol) to (toRow, toCol) is a valid move for the queen
	protected boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells,
			List<Move> moveHistory) {
		// Check if the move is along a diagonal or a straight line
		boolean validMove = ((fromCol - toCol == fromRow - toRow) || (fromCol - toCol == toRow - fromRow)
				|| (toCol - fromCol == fromRow - toRow) || (toCol - fromCol == toRow - fromRow))
				|| (fromRow == toRow || fromCol == toCol);

		// Check if the path is clear for the queen move
		if (validMove) {
			if (!isPathClear(fromRow, fromCol, toRow, toCol, boardCells)) {
				validMove = false;
			}
		}

		// Check if the destination cell has a piece of the same color
		if (boardCells[toRow][toCol].hasPiece() && boardCells[toRow][toCol].getColor().equals(color)) {
			validMove = false;
		}

		// Check if the destination cell has a king
		if ((boardCells[toRow][toCol].hasPiece() && boardCells[toRow][toCol].getPiece() instanceof King)) {
			validMove = false;
		}

		return validMove;
	}

	// Checks if the path is clear for the queen move
	private static boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells) {
		// Check if the move is along a horizontal, vertical, or diagonal path
		if (fromRow == toRow || fromCol == toCol || Math.abs(toRow - fromRow) == Math.abs(toCol - fromCol)) {
			// Determine the direction of the path
			int rowIncrement;
			int colIncrement;

			if (toRow > fromRow) {
				rowIncrement = 1; // Move down
			} else if (toRow < fromRow) {
				rowIncrement = -1; // Move up
			} else {
				rowIncrement = 0; // No vertical movement
			}

			if (toCol > fromCol) {
				colIncrement = 1; // Move right
			} else if (toCol < fromCol) {
				colIncrement = -1; // Move left
			} else {
				colIncrement = 0; // No horizontal movement
			}

			int currentRow = fromRow + rowIncrement;
			int currentCol = fromCol + colIncrement;

			// Iterate over the path to check for obstructions
			while (currentRow != toRow || currentCol != toCol) {
				if (boardCells[currentRow][currentCol].hasPiece()) {
					// There is a piece in the way
					return false;
				}

				// Move to the next square along the path
				currentRow += rowIncrement;
				currentCol += colIncrement;
			}

			// The path is clear
			return true;
		}

		// Not a valid path for the queen
		return false;
	}

	// Checks if the queen's move puts the opponent's king in check
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

	// Checks if the previous move resulted in a check for the opponent
	protected boolean ifWasCheck(int fromRow, int fromCol, int toRow, int toCol, BoardCell[][] boardCells,
			List<Move> moveHistory) {
		boolean isCheck = false;

		String playerColor = moveHistory.get(moveHistory.size() - 2).getMovedPiece().getColor();

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

	// Checks if the move is a valid check move for the queen
	protected boolean isCheckMove(int fromRow, int fromCol, int toRow, int toCol, int kingRow, int kingCol,
			BoardCell[][] boardCells, List<Move> moveHistory) {
		// Check if the move is along a diagonal or a straight line
		boolean validMove = ((kingCol - toCol == kingRow - toRow) || (kingCol - toCol == toRow - kingRow)
				|| (toCol - kingCol == kingRow - toRow) || (toCol - kingCol == toRow - kingRow))
				|| (kingRow == toRow || kingCol == toCol);

		// Check if the path is clear for the queen move
		if (validMove) {
			if (!isPathClear(toRow, toCol, kingRow, kingCol, boardCells)) {
				validMove = false;
			}
		}

		return validMove;
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

	// Placeholder methods (not implemented)
	@Override
	protected int gethasMovedDouble() {
		return 0; // TODO: Implement method
	}

	@Override
	protected boolean ifPromotion(int selectedRow, int selectedCol, int row, int col, BoardCell[][] boardCells) {
		return false; // TODO: Implement method
	}

	// Retrieves a list of valid moves for the queen from a selected position
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

	@Override
	protected boolean ifWasCheck(int startRow, int startCol, int endRow, int endCol, int row, int col,
			BoardCell[][] boardCells, List<Move> moveHistory) {
		// TODO Auto-generated method stub
		return false;
	}
}
