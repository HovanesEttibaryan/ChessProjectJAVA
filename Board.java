import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;


public class Board extends JPanel implements ActionListener, MouseListener {
    BoardCell[][] boardCells;
    int selectedRow = -1;
    int selectedCol = -1;
    int row;
    int col;
    int moveCount = 0;
    static List<Move> moveHistory = new ArrayList<>();
    /*
    static ImageIcon grey_circle = new ImageIcon("grey_circle.png");
    static ImageIcon grey_circle_empty = new ImageIcon("grey_circle_empty.png");
    */
    boolean whitekingincheck = false;
    boolean blackkingincheck = false;
    Pieces piece;
    static List<Move> hiddenchecks = new ArrayList<>();
    static boolean check_mate = false;

    public Board() {
        // Set the layout of the panel to a grid with 8 rows and 8 columns
        this.setLayout(new GridLayout(8, 8));

        // Create a 2D array to represent the board cells
        boardCells = new BoardCell[8][8];

        // Populate the board with buttons and set their properties
        for (int r = 0; r < boardCells.length; r++) {
            for (int c = 0; c < boardCells[0].length; c++) {
                boardCells[r][c] = new BoardCell();
                boardCells[r][c].button.setBorderPainted(false); // Remove the border
                add(boardCells[r][c].button);

                // Set background colors based on the position
                if ((r + c) % 2 == 1) {
                    boardCells[r][c].button.setBackground(Color.decode("#769656")); // Dark color
                } else if ((r + c) % 2 == 0) {
                    boardCells[r][c].button.setBackground(Color.decode("#eeeed2")); // Light color
                }

                // Add ActionListener and MouseListener to each button
                boardCells[r][c].button.addActionListener(this);
                boardCells[r][c].button.addMouseListener(this);
            }
        }

        //White Pieces
        // Create white pawns
        Pawn white_p1 = new Pawn("white");
        ImageIcon white_p1_icon = white_p1.getPieceIcon();
        boardCells[6][0].button.setIcon(white_p1_icon); // Set pawn icon for the button
        boardCells[6][0].setPiece(white_p1);

        Pawn white_p2 = new Pawn("white");
        ImageIcon white_p2_icon = white_p2.getPieceIcon();
        boardCells[6][1].button.setIcon(white_p2_icon);
        boardCells[6][1].setPiece(white_p2);

        Pawn white_p3 = new Pawn("white");
        ImageIcon white_p3_icon = white_p3.getPieceIcon();
        boardCells[6][2].button.setIcon(white_p3_icon);
        boardCells[6][2].setPiece(white_p3);
        
        Pawn white_p4 = new Pawn("white");
        ImageIcon white_p4_icon = white_p4.getPieceIcon();
        boardCells[6][3].button.setIcon(white_p4_icon);
        boardCells[6][3].setPiece(white_p4);

        Pawn white_p5 = new Pawn("white");
        ImageIcon white_p5_icon = white_p5.getPieceIcon();
        boardCells[6][4].button.setIcon(white_p5_icon);
        boardCells[6][4].setPiece(white_p5);

        Pawn white_p6 = new Pawn("white");
        ImageIcon white_p6_icon = white_p6.getPieceIcon();
        boardCells[6][5].button.setIcon(white_p6_icon);
        boardCells[6][5].setPiece(white_p6);

        Pawn white_p7 = new Pawn("white");
        ImageIcon white_p7_icon = white_p7.getPieceIcon();
        boardCells[6][6].button.setIcon(white_p7_icon);
        boardCells[6][6].setPiece(white_p7);
        
        Pawn white_p8 = new Pawn("white");
        ImageIcon white_p8_icon = white_p8.getPieceIcon();
        boardCells[6][7].button.setIcon(white_p8_icon);
        boardCells[6][7].setPiece(white_p8);

        //Create white rooks
        Rook white_rook1 = new Rook("white");
        ImageIcon white_rook1_icon = white_rook1.getPieceIcon();
        boardCells[7][0].button.setIcon(white_rook1_icon);
        boardCells[7][0].setPiece(white_rook1);

        Rook white_rook2 = new Rook("white");
        ImageIcon white_rook2_icon = white_rook2.getPieceIcon();
        boardCells[7][7].button.setIcon(white_rook2_icon);
        boardCells[7][7].setPiece(white_rook2);

        //Create white king
        King white_king = new King("white");
        ImageIcon white_king_icon = white_king.getPieceIcon();
        boardCells[7][4].button.setIcon(white_king_icon);
        boardCells[7][4].setPiece(white_king);
        
        //Create white bishops
        Bishop white_bishop1 = new Bishop("white");
        ImageIcon white_bishop1_icon = white_bishop1.getPieceIcon();
        boardCells[7][5].button.setIcon(white_bishop1_icon);
        boardCells[7][5].setPiece(white_bishop1);
        
        Bishop white_bishop2 = new Bishop("white");
        ImageIcon white_bishop2_icon = white_bishop2.getPieceIcon();
        boardCells[7][2].button.setIcon(white_bishop2_icon);
        boardCells[7][2].setPiece(white_bishop2);
        
        //Create white queen
        Queen white_queen = new Queen("white");
        ImageIcon white_queen_icon = white_queen.getPieceIcon();
        boardCells[7][3].button.setIcon(white_queen_icon);
        boardCells[7][3].setPiece(white_queen);
        
        //Create white knights
        Knight white_knight1 = new Knight("white");
        ImageIcon white_knight1_icon = white_knight1.getPieceIcon();
        boardCells[7][1].button.setIcon(white_knight1_icon);
        boardCells[7][1].setPiece(white_knight1);
        
        Knight white_knight2 = new Knight("white");
        ImageIcon white_knight2_icon = white_knight1.getPieceIcon();
        boardCells[7][6].button.setIcon(white_knight2_icon);
        boardCells[7][6].setPiece(white_knight2);
        
        //Black Pieces
        //Create Black pawns
        Pawn black_p1 = new Pawn("black");
        ImageIcon black_p1_icon = black_p1.getPieceIcon();
        boardCells[1][0].button.setIcon(black_p1_icon); // Set pawn icon for the button
        boardCells[1][0].setPiece(black_p1);
        
        Pawn black_p2 = new Pawn("black");
        ImageIcon black_p2_icon = black_p1.getPieceIcon();
        boardCells[1][1].button.setIcon(black_p2_icon); // Set pawn icon for the button
        boardCells[1][1].setPiece(black_p2);
        
        Pawn black_p3 = new Pawn("black");
        ImageIcon black_p3_icon = black_p3.getPieceIcon();
        boardCells[1][2].button.setIcon(black_p3_icon); // Set pawn icon for the button
        boardCells[1][2].setPiece(black_p3);
        
        Pawn black_p4 = new Pawn("black");
        ImageIcon black_p4_icon = black_p4.getPieceIcon();
        boardCells[1][3].button.setIcon(black_p4_icon); // Set pawn icon for the button
        boardCells[1][3].setPiece(black_p4);
        
        Pawn black_p5 = new Pawn("black");
        ImageIcon black_p5_icon = black_p5.getPieceIcon();
        boardCells[1][4].button.setIcon(black_p5_icon); // Set pawn icon for the button
        boardCells[1][4].setPiece(black_p5);
        
        Pawn black_p6 = new Pawn("black");
        ImageIcon black_p6_icon = black_p6.getPieceIcon();
        boardCells[1][5].button.setIcon(black_p6_icon); // Set pawn icon for the button
        boardCells[1][5].setPiece(black_p6);
        
        Pawn black_p7 = new Pawn("black");
        ImageIcon black_p7_icon = black_p7.getPieceIcon();
        boardCells[1][6].button.setIcon(black_p7_icon); // Set pawn icon for the button
        boardCells[1][6].setPiece(black_p7);
        
        Pawn black_p8 = new Pawn("black");
        ImageIcon black_p8_icon = black_p8.getPieceIcon();
        boardCells[1][7].button.setIcon(black_p8_icon); // Set pawn icon for the button
        boardCells[1][7].setPiece(black_p8);
        
        //Create black rooks
        Rook black_rook1 = new Rook("black");
        ImageIcon black_rook1_icon = black_rook1.getPieceIcon();
        boardCells[0][0].button.setIcon(black_rook1_icon); 
        boardCells[0][0].setPiece(black_rook1);
        
        Rook black_rook2 = new Rook("black");
        ImageIcon black_rook2_icon = black_rook2.getPieceIcon();
        boardCells[0][7].button.setIcon(black_rook2_icon); 
        boardCells[0][7].setPiece(black_rook2);
        
        //Create black king
        King black_king = new King("black");
        ImageIcon black_king_icon = black_king.getPieceIcon();
        boardCells[0][4].button.setIcon(black_king_icon); 
        boardCells[0][4].setPiece(black_king);
        	
        //Create black bishops
        Bishop black_bishop1 = new Bishop("black");
        ImageIcon black_bishop1_icon = black_bishop1.getPieceIcon();
        boardCells[0][2].button.setIcon(black_bishop1_icon); 
        boardCells[0][2].setPiece(black_bishop1);
        
        Bishop black_bishop2 = new Bishop("black");
        ImageIcon black_bishop2_icon = black_bishop2.getPieceIcon();
        boardCells[0][5].button.setIcon(black_bishop2_icon); 
        boardCells[0][5].setPiece(black_bishop2);
        
        //Create black queen
        Queen black_queen = new Queen("black");
        ImageIcon black_queen_icon = black_queen.getPieceIcon();
        boardCells[0][3].button.setIcon(black_queen_icon); 
        boardCells[0][3].setPiece(black_queen);
        
        //Create black knights
        Knight black_knight1 = new Knight("black");
        ImageIcon black_knight1_icon = black_knight1.getPieceIcon();
        boardCells[0][1].button.setIcon(black_knight1_icon);
        boardCells[0][1].setPiece(black_knight1);
        
        Knight black_knight2 = new Knight("black");
        ImageIcon black_knight2_icon = black_knight1.getPieceIcon();
        boardCells[0][6].button.setIcon(black_knight2_icon);
        boardCells[0][6].setPiece(black_knight2);
        
        // Add the mouse listener to the panel
        addMouseListener(this);
       
        createPopup();
    }


private void createPopup() {
    //Create the popup window and set its basics properties
    JFrame popupFrame = new JFrame("Chess");
    popupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    popupFrame.setSize(600,450);
    popupFrame.setLocationRelativeTo(null); // Centers the window on the screen
    popupFrame.setResizable(false); // Keeps the popup size fixed

    //Create the main penel that will hold the title and button
    JPanel popupPanel = new JPanel();

    //Stack everything vertically with the title above button
    popupPanel.setLayout(new BoxLayout(popupPanel, BoxLayout.Y_AXIS));

    //Set the background of the popup
    popupPanel.setBackground(Color.decode("#3e3d32"));

    //Create title text
    JLabel titleLabel = new JLabel("Chess");
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally
    titleLabel.setForeground(Color.WHITE); //Text color
    titleLabel.setFont(new Font("Serif", Font.BOLD, 42)); // Font style and size

    //Create  JButton with rounded corners.
    JButton startButton = new JButton("PLAY"){
        @Override
        protected void paintComponent(Graphics g){
            //Graphics@D gives smoother and better drawing tools
            Graphics2D g2 = (Graphics2D) g.create();

            //Makes rounded corners look smooth
            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            //Use button's current background color
            g2.setColor(getBackground());

            //Draws the rounded rectangle that acts as the button background
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

            //Let JButton draw the text "PLAY" on top of background
            super.paintComponent(g);
            g2.dispose();
        }
    };

    //Center the button horizontally
    startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    //Set the button's normal size
    startButton.setPreferredSize(new Dimension(200, 70));
    startButton.setMaximumSize(new Dimension(200, 70));

    //Style the button text
    startButton.setFont(new Font("Arial", Font.BOLD, 24));
    startButton.setForeground(Color.decode("#2b2b2b"));

    //Set the normal button color
    startButton.setBackground(Color.decode("#d9a441"));

    //Remove the default Swing button Styling so our custom rounded style shows clearly
    startButton.setFocusPainted(false);
    startButton.setBorderPainted(false);
    startButton.setContentAreaFilled(false);
    startButton.setOpaque(false);
    
    //Add hover effects for the button
    startButton.addMouseListener(new MouseAdapter(){
        @Override
        public void mouseEntered(MouseEvent e){
            //Make the button brighter when mouse is over it
            startButton.setBackground(Color.decode("#f0bd55"));

            //Slightly enlarge button when hovered
            startButton.setPreferredSize(new Dimension(220, 80));
            startButton.setMaximumSize(new Dimension(220, 80));

            //Update layout and  redraw the button after changine size and color
            popupPanel.revalidate();
            startButton.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e){
            //Return button to normal color
            startButton.setBackground(Color.decode("#d9a441"));

            //Return the button to its normal size
            startButton.setPreferredSize(new Dimension(200, 70));
            startButton.setMaximumSize(new Dimension(200, 70));

            //Update the layout and redraw button
            popupPanel.revalidate();
            startButton.repaint();
        }
    });

    //When button is clicked close the popup and show the chess board
    startButton.addActionListener(e ->{
        popupFrame.dispose();
        showChessBoard();
    });

    //Add flexible space above the title so that the content is vertically centered
    popupPanel.add(Box.createVerticalGlue());

    //Add title to panel
    popupPanel.add(titleLabel);

    //Add fixed space between title and button
    popupPanel.add(Box.createRigidArea(new Dimension(0, 40)));

    //Add play button to panel
    popupPanel.add(startButton);

    //Add space below button to keep everything centered
    popupPanel.add(Box.createVerticalGlue());

    //Put panel inside popup window
    popupFrame.add(popupPanel);

    //Show the popup window
    popupFrame.setVisible(true);
}


    ImageIcon getScaledImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }


    
    private void showChessBoard() {
        JFrame frame = new JFrame("Chess Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Add your Board to the center of the BorderLayout
        mainPanel.add(this, BorderLayout.CENTER);

        // Set the background color for other regions (North, South, East, West)
        mainPanel.setBackground(Color.decode("#3e3d32"));

        // Create empty panels for other regions with a black background and adjusted borders
        JPanel northPanel = createRegionPanel();
        JPanel southPanel = createRegionPanel();
        JPanel eastPanel = createRegionPanelWithLargerWidth(); // Adjusted width
        JPanel westPanel = createRegionPanel();

        // Add Instruction button to the east panel
        JButton instructionButton = new JButton("Instructions");
        instructionButton.setPreferredSize(new Dimension(200, 75)); // Set button size
        instructionButton.setBackground(Color.decode("#769656")); // Set button background color
        instructionButton.setForeground(Color.WHITE); // Set button text color

        // Set the font size for the button's text
        Font buttonFont = instructionButton.getFont();
        instructionButton.setFont(new Font(buttonFont.getName(), Font.PLAIN, 25)); // Adjust the font size

        instructionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInstructionPopup();
            }
        });

        // Create a panel for the button and set its layout to BorderLayout
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(instructionButton, BorderLayout.EAST); // Add button to the right of the panel
        eastPanel.add(buttonPanel, BorderLayout.EAST); // Add button panel to the east panel

     // Create a JLabel for the text in the south panel
        JLabel southLabel = new JLabel("a          b          c           d          e          f          g          h                                                    ");
        southLabel.setForeground(Color.WHITE); // Set text color
        southLabel.setHorizontalAlignment(JLabel.LEFT); // Align the text to the left
        southLabel.setFont(new Font(southLabel.getFont().getName(), Font.PLAIN, 25)); // Set the initial font size

        // Add the JLabel to the south panel
        southPanel.add(southLabel, BorderLayout.CENTER);
        
     // Create a JLabel for the vertical text in the west panel
        JLabel westLabel = new JLabel("<html>1<br><br><br>2<br><br><br>3<br><br><br>4<br><br><br>5<br><br><br>6<br><br><br>7<br><br><br>8</html>");
        westLabel.setForeground(Color.WHITE); // Set text color
        westLabel.setHorizontalAlignment(JLabel.RIGHT); // Align the text to the right
        westLabel.setFont(new Font(westLabel.getFont().getName(), Font.PLAIN, 25)); // Set the font size

        // Add the JLabel to the west panel
        westPanel.add(westLabel, BorderLayout.WEST);



        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        mainPanel.add(eastPanel, BorderLayout.EAST);
        mainPanel.add(westPanel, BorderLayout.WEST);

        frame.getContentPane().add(mainPanel);
        frame.setSize(1300, 900); // Adjusted frame size
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }



    private void showInstructionPopup() {
        // Create a popup with an image background
        JFrame popupFrame = new JFrame("Instruction");
        popupFrame.setSize(750, 600);

        JPanel popupPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load and draw your image here
                ImageIcon imageIcon = new ImageIcon("chess_instructions.jpg");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        popupFrame.getContentPane().add(popupPanel);
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupFrame.setLocationRelativeTo(null);
        popupFrame.setVisible(true);
    }

    private JPanel createRegionPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#3e3d32"));
        panel.setBorder(BorderFactory.createMatteBorder(20, 40, 20, 10, Color.decode("#3e3d32"))); // Adjusted border thickness
        return panel;
    }

    private JPanel createRegionPanelWithLargerWidth() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#3e3d32"));
        panel.setBorder(BorderFactory.createMatteBorder(20, 40, 20, 210, Color.decode("#3e3d32"))); // Adjusted border thickness and width
        return panel;
    }


    
    
    // Method required by the MouseListener interface
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.drawString("Resize the frame to see the result", 100, 200);
        // Display a message on the panel
    }
    
    
    @Override
    public void mousePressed(MouseEvent e) {
    	
        
        row = getRowForButton(e.getSource());
        col = getColForButton(e.getSource()); 
        

        if (selectedRow == row && selectedCol == col) {
            selectedRow = -1;
            selectedCol = -1;
            defaultColorsReset();
        } else if (selectedRow == -1 && selectedCol == -1) {
            // No piece is selected yet
            if (boardCells[row][col].hasPiece()) {
                // Select the piece
                selectedRow = row;
                selectedCol = col;
                defaultColorsReset();
                highlightCell(row, col);
                
            }
            
            
        } else {
        	// Check if the move is valid for the selected piece
        	    Pieces selectedPiece = boardCells[selectedRow][selectedCol].getPiece();
        	    String currentPlayerColor = selectedPiece.getColor();  // Assuming you have a method to get the current player's color
        	    
        	    int the_row = row;
            	int the_col = col;
            	
            	boolean hadpiece = false;
                if(boardCells[the_row][the_col].hasPiece()) {
                	hadpiece = true;
                	piece = boardCells[the_row][the_col].getPiece();
                }

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
        	    
        	    Pieces king_piece = boardCells[kingRow][kingCol].getPiece();
        	    
        	    
        	 // Selected piece is a pawn
        	    if (selectedPiece instanceof Pawn) {
        	        Pawn selectedPawn = (Pawn) selectedPiece; // Cast selectedPiece to Pawn
        	        
        	    	boolean ifCheck = false;
        	        
        	        // Check if the move is valid for the selected pawn and if it's the player's turn
        	        if (selectedPawn.isValidMove(selectedRow, selectedCol, row, col, boardCells, moveHistory) && TakeTurns()) {
        	            
        	            // Check for regular move
        	                MovePiece(row, col);
        	                highlightCell(row, col);

        	            
        	            // Check for en passant
        	            if (selectedPiece.isEnPassant(selectedRow, selectedCol, row, col, boardCells, moveHistory)) {
        	                boardCells[selectedRow][col].button.setIcon(null);
        	                boardCells[selectedRow][col].setPiece(null);
        	                
        	            }

        	            // Check for pawn promotion
        	            if (selectedPiece.ifPromotion(selectedRow, selectedCol, row, col, boardCells)) {
        	                PawnPromotion();
        	                selectedPiece = boardCells[row][col].getPiece();
        	                if(ifCheck()) {
            	            	System.out.println("Check!");
            	            	ifCheck = true;
            	            	 //Check if the opponent's king is in check after the move
            	            	if (ifCheckMate()) {
                	            	System.out.print("Check Mate");
                	            	Winner();
                	        	}
        	            	} 
        	            }

        	            if (!CorrectCheckResponse()) {
        	            	System.out.println("Incorrect move after check");
        	            	moveCount--;
        	        		int temprow = selectedRow;
        	            	int tempcol = selectedCol;
        	            	selectedRow = row;
        	            	selectedCol = col;
        	            	row = temprow;
        	            	col = tempcol;
        	            	MovePiece(row,col);
            	            defaultColorsReset();
            	            if(hadpiece == true) {
       	            		 // Restore the taken piece
            	            	RestorePiece();
            	            }
        	            }

        	            if(ifCheck()) {
        	            	System.out.println("Check!");
        	            	ifCheck = true;
        	            	 //Check if the opponent's king is in check after the move
        	            	if (ifCheckMate()) {
            	            	System.out.print("Check Mate");
            	            	Winner();
            	        	}
        	            	 Move move = new Move(selectedRow, selectedCol, row, col, selectedPiece, ifCheck);

         	                moveCount++;
         	                System.out.println(moveCount);
         	                moveHistory.add(move);
    	            	} 
        	            
        	            
     
        	             else {
             	            Move move = new Move(selectedRow, selectedCol, row, col, selectedPiece, ifCheck);

        	                moveCount++;
        	                System.out.println(moveCount);
        	                moveHistory.add(move);
        	            }

        	            

        	            // Reset selectedRow and selectedCol
        	            selectedRow = -1;
        	            selectedCol = -1;
        	        }

        	    	else {
        	            // Handle invalid move
        	        	selectedRow = -1;
        	            selectedCol = -1;
        	            defaultColorsReset();
        	        }
        	    }

        	    else if (selectedPiece instanceof Rook) {
        	    	
        	    	boolean ifCheck = false;

        	        // Selected piece is a rook
        	        Rook rookInstance = (Rook) selectedPiece; // Cast to Rook
        	        
        	        if (rookInstance.isValidMove(selectedRow, selectedCol, row, col, boardCells, moveHistory) && TakeTurns()) {
        	        	MovePiece(row,col);
	        	        highlightCell(row, col);
        	        	
        	        	
        	        	
	        	        if (!CorrectCheckResponse()) {
        	            	System.out.println("Incorrect move after check");
        	            	moveCount--;
        	        		int temprow = selectedRow;
        	            	int tempcol = selectedCol;
        	            	selectedRow = row;
        	            	selectedCol = col;
        	            	row = temprow;
        	            	col = tempcol;
        	            	MovePiece(row,col);
            	            defaultColorsReset();
            	            if(hadpiece == true) {
       	            		 // Restore the taken piece
            	            	RestorePiece();
            	            }
        	            }

        	            if(ifCheck()) {
        	            	System.out.println("Check!");
        	            	ifCheck = true;
        	            	 //Check if the opponent's king is in check after the move
        	            	if (ifCheckMate()) {
            	            	System.out.print("Check Mate");
            	            	Winner();
            	        	}
        	            	 Move move = new Move(selectedRow, selectedCol, row, col, selectedPiece, ifCheck);

         	                moveCount++;
         	                System.out.println(moveCount);
         	                moveHistory.add(move);
    	            	} 
        	            
        	            
     
        	             else {
             	            Move move = new Move(selectedRow, selectedCol, row, col, selectedPiece, ifCheck);

        	                moveCount++;
        	                System.out.println(moveCount);
        	                moveHistory.add(move);
        	            }


        	            selectedRow = -1;
        	            selectedCol = -1;
                      
        	        } else {
        	            // Handle invalid move
        	            selectedRow = -1;
        	            selectedCol = -1;
        	            defaultColorsReset();
        	        }
        	    }

        	    else if (selectedPiece instanceof King) {
        	        boolean ifCheck = false;

        	        // Selected piece is a king
        	        King kingInstance = (King) selectedPiece;

                    
        	    if (TakeTurns() && (
        kingInstance.isValidMove(selectedRow, selectedCol, row, col, boardCells, moveHistory)
        || kingInstance.KingSideCastle(selectedRow, selectedCol, row, col, boardCells, moveHistory)
        || kingInstance.QueenSideCastle(selectedRow, selectedCol, row, col, boardCells, moveHistory)
)) {
        	        	        	        	
        	        	if(kingInstance.KingSideCastle(selectedRow, selectedCol, row, col, boardCells, moveHistory)) {
        	        		System.out.println("King Side Castle");
        	        		KingSideCastle();
        	        		// Store the move in the list
        	                Move move = new Move(selectedRow, selectedCol, row, col, selectedPiece, ifCheck);
        	                moveCount++;
    	                    System.out.println(moveCount);
    	                    moveHistory.add(move);
    	                    
    	                
        	        	}
        	        	
        	        	else if(kingInstance.QueenSideCastle(selectedRow, selectedCol, row, col, boardCells, moveHistory)) {
        	        		System.out.println("Queen Side Castle");
        	        		QueenSideCastle();
        	        		Move move = new Move(selectedRow, selectedCol, row, col, selectedPiece, ifCheck);
        	                moveCount++;
    	                    System.out.println(moveCount);
    	                    moveHistory.add(move);
    	                    

        	        	}
        	        	else {
	        	        	// Move the king to the new location
	        	        	MovePiece(row, col);
		        	        highlightCell(row, col);
                            kingInstance.setHasMoved();
        	            
		        	        if (!CorrectCheckResponse()) {
	        	            	System.out.println("Incorrect move after check");
	        	            	moveCount--;
	        	        		int temprow = selectedRow;
	        	            	int tempcol = selectedCol;
	        	            	selectedRow = row;
	        	            	selectedCol = col;
	        	            	row = temprow;
	        	            	col = tempcol;
	        	            	MovePiece(row,col);
	            	            defaultColorsReset();
	            	            if(hadpiece == true) {
	       	            		 // Restore the taken piece
	            	            	RestorePiece();
	            	            }
	        	            } 
		        	        
	        	            if(ifCheck()) {
	        	            	System.out.println("Check!");
	        	            	ifCheck = true;
	        	            	 //Check if the opponent's king is in check after the move
	        	            	if (ifCheckMate()) {
	            	            	System.out.print("Check Mate");
	            	            	Winner();
	            	        	}
	        	            	
	        	            	Move move = new Move(selectedRow, selectedCol, row, col, selectedPiece, ifCheck);

	         	                moveCount++;
	         	                System.out.println(moveCount);
	         	                moveHistory.add(move);
	    	            	} 
	        	            
	        	            
	     
	        	            else {
	            	            Move move = new Move(selectedRow, selectedCol, row, col, selectedPiece, ifCheck);

	        	                moveCount++;
	        	                System.out.println(moveCount);
	        	                moveHistory.add(move);
	        	            }
        	                selectedRow = -1;
        	                selectedCol = -1;
        	        	}
        	        } else {
        	            // Handle invalid move
        	            selectedRow = -1;
        	            selectedCol = -1;
        	            defaultColorsReset();
        	        }
        	    }

        	    else if(selectedPiece instanceof Bishop) {
        	   
        	    	boolean ifCheck = false;

        	    	Bishop bishopInstance = (Bishop) selectedPiece;

        	        if (bishopInstance.isValidMove(selectedRow, selectedCol, row, col, boardCells, moveHistory) && TakeTurns()) {
        	        	
        	        	MovePiece(row,col);
	        	        highlightCell(row, col);
	        	        

	        	        if (!CorrectCheckResponse()) {
        	            	System.out.println("Incorrect move after check");
        	            	moveCount--;
        	        		int temprow = selectedRow;
        	            	int tempcol = selectedCol;
        	            	selectedRow = row;
        	            	selectedCol = col;
        	            	row = temprow;
        	            	col = tempcol;
        	            	MovePiece(row,col);
            	            defaultColorsReset();
            	            if(hadpiece == true) {
       	            		 // Restore the taken piece
            	            	RestorePiece();
            	            }
        	            }

        	            if(ifCheck()) {
        	            	System.out.println("Check!");
        	            	ifCheck = true;
        	            	 //Check if the opponent's king is in check after the move
        	            	if (ifCheckMate()) {
            	            	System.out.print("Check Mate");
            	            	Winner();
            	        	}
        	            	 Move move = new Move(selectedRow, selectedCol, row, col, selectedPiece, ifCheck);

         	                moveCount++;
         	                System.out.println(moveCount);
         	                moveHistory.add(move);
    	            	} 
        	            
        	            
     
        	             else {
             	            Move move = new Move(selectedRow, selectedCol, row, col, selectedPiece, ifCheck);

        	                moveCount++;
        	                System.out.println(moveCount);
        	                moveHistory.add(move);
        	            }

        	            selectedRow = -1;
        	            selectedCol = -1;
                        
        	        } else {
        	        	// Handle invalid move
        	        	selectedRow = -1;
        	            selectedCol = -1;
        	            defaultColorsReset();
        	        }
        	    }
        	    else if (selectedPiece instanceof Queen) {
        	        boolean ifCheck = false;
        	        
        	        Queen queenInstance = (Queen) selectedPiece;

        	        if (queenInstance.isValidMove(selectedRow, selectedCol, row, col, boardCells, moveHistory) && TakeTurns()) {
        	        	
        	        	
        	            MovePiece(row, col);
        	            highlightCell(row, col);        	            
	            
        	            if (!CorrectCheckResponse()) {
        	            	System.out.println("Incorrect move after check");
        	            	moveCount--;
        	        		int temprow = selectedRow;
        	            	int tempcol = selectedCol;
        	            	selectedRow = row;
        	            	selectedCol = col;
        	            	row = temprow;
        	            	col = tempcol;
        	            	MovePiece(row,col);
            	            defaultColorsReset();
            	            if(hadpiece == true) {
       	            		 // Restore the taken piece
            	            	RestorePiece();
            	            }
        	            }
        	            
        	            if(ifCheck()) {
        	            	System.out.println("Check!");
        	            	ifCheck = true;
        	            	 //Check if the opponent's king is in check after the move
        	            	if (ifCheckMate()) {
            	            	System.out.print("Check Mate");
            	            	Winner();
            	        	}
        	            	
        	            	Move move = new Move(selectedRow, selectedCol, row, col, selectedPiece, ifCheck);

         	                moveCount++;
         	                System.out.println(moveCount);
         	                moveHistory.add(move);
    	            	} 
        	            
        	            
     
        	             else {
             	            Move move = new Move(selectedRow, selectedCol, row, col, selectedPiece, ifCheck);

        	                moveCount++;
        	                System.out.println(moveCount);
        	                moveHistory.add(move);
        	            }

        	            selectedRow = -1;
        	            selectedCol = -1;

        	        } else {
        	            // Handle invalid move
        	            selectedRow = -1;
        	            selectedCol = -1;
        	            defaultColorsReset();
        	        }
        	    }
        	    else if(selectedPiece instanceof Knight) {
        	    	
        	    	boolean ifCheck = false;

        	    	Knight knightInstance = (Knight) selectedPiece;
        	    	
        	        if (knightInstance.isValidMove(selectedRow, selectedCol, row, col, boardCells, moveHistory) && TakeTurns()) {
        	        	
        	        	MovePiece(row,col);
	        	        highlightCell(row, col); 
       	        	

	        	        if (!CorrectCheckResponse()) {
        	            	System.out.println("Incorrect move after check");
        	            	moveCount--;
        	        		int temprow = selectedRow;
        	            	int tempcol = selectedCol;
        	            	selectedRow = row;
        	            	selectedCol = col;
        	            	row = temprow;
        	            	col = tempcol;
        	            	MovePiece(row,col);
            	            defaultColorsReset();
            	            if(hadpiece == true) {
       	            		 // Restore the taken piece
            	            	RestorePiece();
            	            }
        	            } 
	        	        
        	            if(ifCheck()) {
        	            	System.out.println("Check!");
        	            	ifCheck = true;
        	            	 //Check if the opponent's king is in check after the move
        	            	if (ifCheckMate()) {
            	            	System.out.print("Check Mate");
            	            	Winner();
            	        	}
        	            	
        	            	 Move move = new Move(selectedRow, selectedCol, row, col, selectedPiece, ifCheck);

         	                moveCount++;
         	                System.out.println(moveCount);
         	                moveHistory.add(move);
    	            	} 
        	            
        	            
     
        	            else {
            	            Move move = new Move(selectedRow, selectedCol, row, col, selectedPiece, ifCheck);

        	                moveCount++;
        	                System.out.println(moveCount);
        	                moveHistory.add(move);
        	            }

        	            selectedRow = -1;
        	            selectedCol = -1;
        	        	
        	        } else {
        	        	// Handle invalid move
        	        	InvalidMove();
        	        }
        	    }
        	    
        	}
       
        
        Repetition();
       }
    
    boolean CorrectCheckResponse() {
        boolean correctresp = true;

        if (moveCount >= 3) {
            Move lastMove = moveHistory.get(moveHistory.size() - 1);

            // Check regular checks
            if (lastMove.getIfCheck() && ifCheck()) {
                    correctresp = false;
                    defaultColorsReset();      
            }
        }

        return correctresp;
    }
    
    boolean DiffCorrectCheckResponse() {
        boolean correctresp = true;

        if (moveCount >= 3) {

            // Check regular checks
            if (ifCheck()) {
                    correctresp = false;
                    defaultColorsReset();      
            }
        }

        return correctresp;
    }


    boolean ifCheckMate() {
        boolean ismate = true;
        String currentPlayerColor;
        int count = 0;
        Pieces piecee;
        
        // Check if moveCount is an even number
        if (moveCount % 2 == 0) {
            currentPlayerColor = "white";  // Set currentPlayerColor to "white"
        } else {
            currentPlayerColor = "black";  // Set currentPlayerColor to "black"
        }

        if(ifCheck()) {
            for (int r = 0; r < boardCells.length; r++) {
                for (int c = 0; c < boardCells[0].length; c++) {
                    if (boardCells[r][c].hasPiece() && !boardCells[r][c].getColor().equals(currentPlayerColor)) {
                       piecee = boardCells[r][c].getPiece();
                        System.out.println(piecee);
                        System.out.println("No Move");

                        // Check if there is any valid move that can break the check
                        List<Move> validMoves = piecee.getValidMoves(r, c, boardCells);
                        if (!validMoves.isEmpty()) {
                            for (Move move : validMoves) {
                                boolean hadPiece = false;
                                System.out.println(piecee);
                                int startRow = move.getStartRow();
                                int startCol = move.getStartCol();
                                int endRow = move.getEndRow();
                                int endCol = move.getEndCol();
                                System.out.println(endRow);
                                System.out.println(endCol);
                                
                                if (boardCells[move.getEndRow()][move.getEndCol()].hasPiece()) {
                                	hadPiece = true;
                                    piece = boardCells[move.getEndRow()][move.getEndCol()].getPiece();
                                    System.out.print(piece);

                                    count++;
                                    System.out.println(count);
                                 }
                               

                                makeMove(move.getStartRow(), move.getStartCol(), move.getEndRow(), move.getEndCol());
                                System.out.println("Move made");
                                count++;
                               System.out.println(count);

                                if (!ifCheck()) {
                                	ismate = false;
                                }

                                makeMove(move.getEndRow(), move.getEndCol(), move.getStartRow(), move.getStartCol());
                                System.out.println("Move Taken Back");
                                count++;
                                System.out.println(count);

                                if (hadPiece) {
                                	selectedRow = move.getEndRow();
                                	selectedCol = move.getEndCol();
                                	
                                    // Restore the taken piece
                                    RestorePiece();
                                    System.out.println("Piece restored");
                                }
                            }
                        }
                    }
                }
            }
        }

        return ismate;
    }


    
    void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
    	boardCells[toRow][toCol].button.setIcon(boardCells[fromRow][fromCol].button.getIcon());
        boardCells[toRow][toCol].setPiece(boardCells[fromRow][fromCol].getPiece());
        boardCells[fromRow][fromCol].button.setIcon(null);
        boardCells[fromRow][fromCol].setPiece(null);

        // Clear the selection and reset the border color
        boardCells[fromRow][fromCol].button.setBorder(null);

    }


    boolean ifCheck() {
    	boolean check = false;
    	     
    	for (int r = 0; r < boardCells.length; r++) {
            for (int c = 0; c < boardCells[0].length; c++) {
                if(boardCells[r][c].hasPiece()) {
                	Pieces piece = boardCells[r][c].getPiece();
                	if(piece.ifCheck(r, c, r, c, boardCells, moveHistory)) {
                		check = true;
                	}
                }
            }
        }
    	
    	return check;
    }

    	
    boolean TakeTurns() {
	    Pieces selectedPiece = boardCells[selectedRow][selectedCol].getPiece();
    	boolean taketurns = false;
	    
    	if(moveCount%2==0 && selectedPiece.getColor().equals("white")) {
    		taketurns = true;
    	}
    	
    	if(moveCount>0 && moveCount%2==1 && selectedPiece.getColor().equals("black")) {
    		taketurns = true;
    	}
    	
    	return taketurns;
    }
 
    private void highlightCell(int row, int col) {
        // Set the background color based on the cell's position
        if ((row + col) % 2 == 1) {
            boardCells[row][col].button.setBackground(Color.decode("#baca44")); // Dark color
        } else {
            boardCells[row][col].button.setBackground(Color.decode("#fcfc8d")); // Light color
        }
    }
    
    private void defaultColorsReset() {
    	for (int r = 0; r < boardCells.length; r++) {
            for (int c = 0; c < boardCells[0].length; c++) {
                // Set background colors based on the position
                if ((r + c) % 2 == 1) {
                    boardCells[r][c].button.setBackground(Color.decode("#769656")); // Dark color
                } else if ((r + c) % 2 == 0) {
                    boardCells[r][c].button.setBackground(Color.decode("#eeeed2")); // Light color
                }
            }
        }
    }
    
    
    
    public void KingSideCastle() {
    	Pieces selectedPiece = boardCells[selectedRow][selectedCol].getPiece();
	    King kingInstance = (King) selectedPiece;
	    Rook rookInstance1 = (Rook) boardCells[7][7].getPiece();
	    Rook rookInstance2 = (Rook) boardCells[0][7].getPiece();
    	
    	if(kingInstance.KingSideCastle(selectedRow, selectedCol, row, col, boardCells, moveHistory)) {
    		
    		if(kingInstance.getColor().equals("white") && boardCells[7][7].getPiece() instanceof Rook && rookInstance1.KingSideCastle(boardCells)) {
    			MovePiece(row, col);
    	        highlightCell(row, col);
    			boardCells[7][5].button.setIcon(boardCells[7][7].button.getIcon());
    		    boardCells[7][5].setPiece(boardCells[7][7].getPiece());
    		    boardCells[7][7].button.setIcon(null);
    		    boardCells[7][7].setPiece(null);
    		}
    		
    		if(kingInstance.getColor().equals("black") && boardCells[0][7].getPiece() instanceof Rook && rookInstance2.KingSideCastle(boardCells)) {
    			MovePiece(row, col);
    	        highlightCell(row, col);
    			boardCells[0][5].button.setIcon(boardCells[0][7].button.getIcon());
    		    boardCells[0][5].setPiece(boardCells[0][7].getPiece());
    		    boardCells[0][7].button.setIcon(null);
    		    boardCells[0][7].setPiece(null);
    		}
    	}
    	selectedRow = -1;
        selectedCol = -1;
    }
    
    
    public void QueenSideCastle() {
	    Pieces selectedPiece = boardCells[selectedRow][selectedCol].getPiece();
	    King kingInstance = (King) selectedPiece;
	    Rook rookInstance1 = (Rook) boardCells[7][0].getPiece();
	    Rook rookInstance2 = (Rook) boardCells[0][0].getPiece();
	    
    	if(kingInstance.QueenSideCastle(selectedRow, selectedCol, row, col, boardCells, moveHistory)) {
    		
    		if(kingInstance.getColor().equals("white") && boardCells[7][0].getPiece() instanceof Rook && rookInstance1.QueenSideCastle(boardCells)) {
    			MovePiece(row, col);
    	        highlightCell(row, col);
    			boardCells[7][3].button.setIcon(boardCells[7][0].button.getIcon());
    		    boardCells[7][3].setPiece(boardCells[7][0].getPiece());
    		    boardCells[7][0].button.setIcon(null);
    		    boardCells[7][0].setPiece(null);
    		}
    		
    		if(kingInstance.getColor().equals("black") && boardCells[0][0].getPiece() instanceof Rook && rookInstance2.QueenSideCastle(boardCells)) {
    			MovePiece(row, col);
    	        highlightCell(row, col);
    			boardCells[0][3].button.setIcon(boardCells[0][0].button.getIcon());
    		    boardCells[0][3].setPiece(boardCells[0][0].getPiece());
    		    boardCells[0][0].button.setIcon(null);
    		    boardCells[0][0].setPiece(null);
    		}
    	}
    	selectedRow = -1;
        selectedCol = -1;
    }
    
    public void PawnPromotion() {
    	String color = "white";
    	if(moveCount%2 ==0) {
    		color = "white";
    	}
    	if(moveCount%2 ==1) {
    		color = "black";
    	}
    	

    	
    	if(color.equals("white")) {
            // Prompt the user for pawn promotion
            String[] options = {"Queen", "Rook", "Bishop", "Knight"};
            int result = JOptionPane.showOptionDialog(
                    null,
                    "Choose a piece for promotion:",
                    "Pawn Promotion",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            // Handle the user's choice
            switch (result) {
                case 0:
               	 //Queen Promotion logic
               	 
               	 Queen white_queen = new Queen("white");
               	 	MovePiece(row,col);
                    ImageIcon white_queen_icon = white_queen.getPieceIcon();
                    boardCells[row][col].button.setIcon(white_queen_icon);
                    boardCells[row][col].setPiece(white_queen);
                    boardCells[selectedRow][selectedCol].button.setIcon(null); 
      	             boardCells[selectedRow][selectedCol].setPiece(null);
                    break;
                case 1:
               	 //Rook Promotion logic
               	 
               	 Rook white_rook = new Rook("white");
            	 	MovePiece(row,col);
                    ImageIcon white_rook_icon = white_rook.getPieceIcon();
                    boardCells[row][col].button.setIcon(white_rook_icon);
                    boardCells[row][col].setPiece(white_rook);
                    boardCells[selectedRow][selectedCol].button.setIcon(null); 
      	             boardCells[selectedRow][selectedCol].setPiece(null);
                    break;
                case 2:
               	 //Bishop promotion logic
               	 	MovePiece(row,col);

               	 Bishop white_bishop = new Bishop("white");
                    ImageIcon white_bishop_icon = white_bishop.getPieceIcon();
                    boardCells[row][col].button.setIcon(white_bishop_icon);
                    boardCells[row][col].setPiece(white_bishop);
                    boardCells[selectedRow][selectedCol].button.setIcon(null); 
      	             boardCells[selectedRow][selectedCol].setPiece(null);
                    break;
                case 3:
               	 //Knight Promotion logic
               	 	MovePiece(row,col);

               	 Knight white_knight = new Knight("white");
                    ImageIcon white_knight_icon = white_knight.getPieceIcon();
                    boardCells[row][col].button.setIcon(white_knight_icon);
                    boardCells[row][col].setPiece(white_knight);
                    boardCells[selectedRow][selectedCol].button.setIcon(null); 
      	             boardCells[selectedRow][selectedCol].setPiece(null);
                    break;
                default:
                    // Handle unexpected choice
                    break;
            }
    	}
		
		if(color.equals("black")) {
            // Prompt the user for pawn promotion
            String[] options = {"Queen", "Rook", "Bishop", "Knight"};
            int result = JOptionPane.showOptionDialog(
                    null,
                    "Choose a piece for promotion:",
                    "Pawn Promotion",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            // Handle the user's choice
            switch (result) {
                case 0:
	               	 //Queen Promotion logic
               	 	MovePiece(row,col);

	               	 Queen black_queen = new Queen("black");
                    ImageIcon black_queen_icon = black_queen.getPieceIcon();
                    boardCells[row][col].button.setIcon(black_queen_icon);
                    boardCells[row][col].setPiece(black_queen);
                    boardCells[selectedRow][selectedCol].button.setIcon(null); 
	   	             boardCells[selectedRow][selectedCol].setPiece(null);
                    break;
                case 1:
	               	 //Rook Promotion logic
               	 	MovePiece(row,col);

	               	 Rook black_rook = new Rook("black");
                    ImageIcon black_rook_icon = black_rook.getPieceIcon();
                    boardCells[row][col].button.setIcon(black_rook_icon);
                    boardCells[row][col].setPiece(black_rook);
                    boardCells[selectedRow][selectedCol].button.setIcon(null); 
	   	             boardCells[selectedRow][selectedCol].setPiece(null);
                    break;
                case 2:
	               	 //Bishop promotion logic
               	 	MovePiece(row,col);

	               	 Bishop black_bishop = new Bishop("black");
                    ImageIcon black_bishop_icon = black_bishop.getPieceIcon();
                    boardCells[row][col].button.setIcon(black_bishop_icon);
                    boardCells[row][col].setPiece(black_bishop);
                    boardCells[selectedRow][selectedCol].button.setIcon(null); 
	   	             boardCells[selectedRow][selectedCol].setPiece(null);
                    break;
                case 3:
	               	 //Knight Promotion logic
               	 	MovePiece(row,col);

	               	 Knight black_knight = new Knight("black");
                    ImageIcon black_knight_icon = black_knight.getPieceIcon();
                    boardCells[row][col].button.setIcon(black_knight_icon);
                    boardCells[row][col].setPiece(black_knight);
                    boardCells[selectedRow][selectedCol].button.setIcon(null); 
	   	             boardCells[selectedRow][selectedCol].setPiece(null);
                    break;
                default:
                    // Handle unexpected choice
                    break;
            }
		}
    }
    
    
    void RestorePiece() {
    	String color = null;
    	if(moveCount%2==0) {
    		color = "white";
    	}
    	else if(moveCount%2==1) {
    		color = "black";
    	}
    	
    	if(piece instanceof Pawn) {
    		Pawn piece = new Pawn(color);
            ImageIcon piece_icon = piece.getPieceIcon();
            boardCells[selectedRow][selectedCol].button.setIcon(piece_icon); 
            boardCells[selectedRow][selectedCol].setPiece(piece);
    	}
    	
    	else if(piece instanceof Bishop) {
    		Bishop piece = new Bishop(color);
            ImageIcon piece_icon = piece.getPieceIcon();
            boardCells[selectedRow][selectedCol].button.setIcon(piece_icon); 
            boardCells[selectedRow][selectedCol].setPiece(piece);
    	}
    	
    	else if(piece instanceof Knight) {
    		Knight piece = new Knight(color);
            ImageIcon piece_icon = piece.getPieceIcon();
            boardCells[selectedRow][selectedCol].button.setIcon(piece_icon); 
            boardCells[selectedRow][selectedCol].setPiece(piece);
    	}
    	
    	else if(piece instanceof Rook) {
    		Rook piece = new Rook(color);
            ImageIcon piece_icon = piece.getPieceIcon();
            boardCells[selectedRow][selectedCol].button.setIcon(piece_icon); 
            boardCells[selectedRow][selectedCol].setPiece(piece);
    	}
    	
    	else if(piece instanceof Queen) {
    		Queen piece = new Queen(color);
            ImageIcon piece_icon = piece.getPieceIcon();
            boardCells[selectedRow][selectedCol].button.setIcon(piece_icon); 
            boardCells[selectedRow][selectedCol].setPiece(piece);
    	}
    }

    private int getRowForButton(Object source) {
        // Helper method to get the row index for a button
        for (int r = 0; r < boardCells.length; r++) {
            for (int c = 0; c < boardCells[0].length; c++) {
                if (boardCells[r][c].button == source) {
                    return r;
                }
            }
        }
        return -1;
    }

    private int getColForButton(Object source) {
        // Helper method to get the column index for a button
        for (int r = 0; r < boardCells.length; r++) {
            for (int c = 0; c < boardCells[0].length; c++) {
                if (boardCells[r][c].button == source) {
                    return c;
                }
            }
        }
        return -1;
    }
    
    public void MovePiece(int row, int col) {
    	boardCells[row][col].button.setIcon(boardCells[selectedRow][selectedCol].button.getIcon());
        boardCells[row][col].setPiece(boardCells[selectedRow][selectedCol].getPiece());
        boardCells[selectedRow][selectedCol].button.setIcon(null);
        boardCells[selectedRow][selectedCol].setPiece(null);

        // Clear the selection and reset the border color
        boardCells[selectedRow][selectedCol].button.setBorder(null);

    }

    public void InvalidMove() {
    	System.out.println("Invalid move");
        selectedRow = -1;
        selectedCol = -1;
        defaultColorsReset();
    }
    
    public void Repetition() {
        if (moveHistory.size() >= 9) {
            Move move1 = moveHistory.get(moveHistory.size() - 9);
            Move move2 = moveHistory.get(moveHistory.size() - 8);
            Move move3 = moveHistory.get(moveHistory.size() - 7);
            Move move4 = moveHistory.get(moveHistory.size() - 6);
            Move move5 = moveHistory.get(moveHistory.size() - 5);
            Move move6 = moveHistory.get(moveHistory.size() - 4);
            Move move7 = moveHistory.get(moveHistory.size() - 3);
            Move move8 = moveHistory.get(moveHistory.size() - 2);
            Move move9 = moveHistory.get(moveHistory.size() - 1);

            if (move1.getStartRow() == move3.getEndRow() && move1.getStartCol() == move3.getEndCol() &&
                move2.getStartRow() == move4.getEndRow() && move2.getStartCol() == move4.getEndCol() &&
                move3.getStartRow() == move5.getEndRow() && move3.getStartCol() == move5.getEndCol() &&
                move4.getStartRow() == move6.getEndRow() && move4.getStartCol() == move6.getEndCol() &&
                move5.getStartRow() == move7.getEndRow() && move5.getStartCol() == move7.getEndCol() &&
                move6.getStartRow() == move8.getEndRow() && move6.getStartCol() == move8.getEndCol() &&
                move7.getStartRow() == move9.getEndRow() && move7.getStartCol() == move9.getEndCol()) {
                // Draw by threefold repetition
                showDrawByRepetitionDialog();
            }
        }
    }

    private void Winner() {
    	String color = "white";
    	if(moveCount%2 ==0) {
    		color = "white";
    	}
    	if(moveCount%2 ==1) {
    		color = "black";
    	}
    	// Show pop-up dialog
        int option = JOptionPane.showOptionDialog(null, "Check Mate!\n"+color+" Won!!", "CheckMate!",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                new Object[] { "Exit" }, "Exit");

        if (option == JOptionPane.YES_OPTION) {
            // Code to exit the game (e.g., exit the application)
            // You can add your logic here to exit the game
            System.out.println("Exiting the game...");
            System.exit(0); // Assuming you want to exit the application
        }
    }

    private void showDrawByRepetitionDialog() {
        // Show pop-up dialog
        int option = JOptionPane.showOptionDialog(null, "Draw by repetition!\nDo you want to exit the game?", "Draw",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                new Object[] { "Exit" }, "Exit");

        if (option == JOptionPane.YES_OPTION) {
            // Code to exit the game (e.g., exit the application)
            // You can add your logic here to exit the game
            System.out.println("Exiting the game...");
            System.exit(0); // Assuming you want to exit the application
        }
    }

    
    int getCount() {
    	return moveCount;
    }

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	   // Add other MouseListener methods if needed

    // ActionListener implementation for buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button clicks if needed
        // (not used in this example)
    }

    // MouseListener implementation for buttons
    @Override
    public void mouseClicked(MouseEvent e) {
        // Handle mouse clicks if needed
        // (not used in this example)
    }

}