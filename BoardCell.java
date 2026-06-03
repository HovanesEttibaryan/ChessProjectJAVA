import javax.swing.ImageIcon;
import javax.swing.JButton;

// Represents a cell on the chessboard
public class BoardCell {
    JButton button;     // Button associated with the cell (for GUI representation)
    Pieces piece;       // Reference to the chess piece in the cell
    private ImageIcon image; // Image associated with the cell's background (if any)
    private int row;       // Row index of the cell on the chessboard
    private int col;       // Column index of the cell on the chessboard

    // Default constructor to initialize the cell with an empty button
    public BoardCell() {
        button = new JButton();
    }

    // Checks if the cell contains a chess piece
    boolean hasPiece() {
        return piece != null;
    }

    // Retrieves the chess piece in the cell
    Pieces getPiece() {
        return piece;
    }

    // Retrieves the color of the chess piece in the cell
    String getColor() {
        return piece.getColor();
    }

    // Sets a chess piece to the cell
    void setPiece(Pieces piece) {
        this.piece = piece;
    }

    // Removes the chess piece from the cell
    void removePiece() {
        piece = null;
        // You may also want to update the button's icon or other properties
        button.setIcon(null);
        // Additional cleanup or UI updates can be done here
    }

    // Retrieves the image associated with the cell's background
    public ImageIcon getImage() {
        return image;
    }

    // Sets the image associated with the cell's background
    public void setImage(ImageIcon image) {
        this.image = image;
    }

    // Retrieves the row index of the cell
    int getRow() {
        return row;
    }

    // Retrieves the column index of the cell
    int getCol() {
        return col;
    }

    // TODO: Consider implementing additional methods based on the requirements

    // Placeholder method (not implemented)
    public boolean getHasMoved() {
        // TODO: Implement method
        return false;
    }

    // Retrieves the ImageIcon associated with the button
    public ImageIcon getIcon() {
        return (ImageIcon) button.getIcon();
    }
}
