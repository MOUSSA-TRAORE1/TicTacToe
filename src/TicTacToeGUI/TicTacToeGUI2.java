import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToeGUI2 extends JFrame implements ActionListener {

    // Enum to represent cell states
    private enum Cell { X, O, EMPTY }

    // 3x3 board for the game
    private Cell[][] board = new Cell[3][3];
    private JButton[][] buttons = new JButton[3][3];
    private Cell currentPlayer;
    private boolean playerIsX;
    private boolean computerPlays;
    private boolean playAgainstComputer;

    // Random number generator for computer moves
    private Random random = new Random();

    // Constructor to set up the game
    public TicTacToeGUI2() {
        // Ask if the game is against the computer or two players
        int modeResponse = JOptionPane.showConfirmDialog(null, "Do you want to play against the computer?", "Choose Mode",
                JOptionPane.YES_NO_OPTION);
        playAgainstComputer = (modeResponse == JOptionPane.YES_OPTION);

        // Ask if the player wants to go first if playing against the computer
        if (playAgainstComputer) {
            int response = JOptionPane.showConfirmDialog(null, "Do you want to go first?", "Choose Turn",
                    JOptionPane.YES_NO_OPTION);
            playerIsX = (response == JOptionPane.YES_OPTION);
            currentPlayer = playerIsX ? Cell.X : Cell.O;
            computerPlays = !playerIsX;
        } else {
            // In two-player mode, player 1 is X and player 2 is O
            currentPlayer = Cell.X;
            computerPlays = false;
        }

        // Initialize the game board with empty cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }

        // Set up JFrame settings
        setTitle("Tic-Tac-Toe Game");
        setSize(300, 300);
        setLayout(new GridLayout(3, 3));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize each button in the 3x3 grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                add(buttons[i][j]);
            }
        }

        setVisible(true);

        // Let computer make the first move if needed
        if (computerPlays && playAgainstComputer) {
            makeComputerMove();
        }
    }

    // Action performed when a button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (computerPlays) return; // Ignore clicks if it's the computer's turn

        JButton clickedButton = (JButton) e.getSource();
        int row = -1, col = -1;

        // Find the button's position on the grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j] == clickedButton) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        // Make the move if the cell is empty
        if (board[row][col] == Cell.EMPTY) {
            board[row][col] = currentPlayer;
            clickedButton.setText(currentPlayer == Cell.X ? "X" : "O");
            clickedButton.setEnabled(false);

            // Check if the game is won or drawn
            if (checkWinner()) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                resetGame();
            } else if (isDraw()) {
                JOptionPane.showMessageDialog(this, "The game is a draw!");
                resetGame();
            } else {
                // Switch players
                switchPlayer();
                if (computerPlays && playAgainstComputer) {
                    makeComputerMove();
                }
            }
        }
    }

    // Method to make the computer's move
    private void makeComputerMove() {
        boolean moveMade = false;

        // Try random moves until finding an empty cell
        while (!moveMade) {
            int row = random.nextInt(3);
            int col = random.nextInt(3);

            if (board[row][col] == Cell.EMPTY) {
                board[row][col] = currentPlayer;
                buttons[row][col].setText(currentPlayer == Cell.X ? "X" : "O");
                buttons[row][col].setEnabled(false);
                moveMade = true;
            }
        }

        // Check if the game is won or drawn
        if (checkWinner()) {
            JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
            resetGame();
        } else if (isDraw()) {
            JOptionPane.showMessageDialog(this, "The game is a draw!");
            resetGame();
        } else {
            // Switch players and set to player's turn
            switchPlayer();
            computerPlays = false;
        }
    }

    // Check if the current player has won
    private boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != Cell.EMPTY && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return true;
            if (board[0][i] != Cell.EMPTY && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return true;
        }
        if (board[0][0] != Cell.EMPTY && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            return true;
        if (board[0][2] != Cell.EMPTY && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return true;

        return false;
    }

    // Check if the board is full
    private boolean isDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == Cell.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    // Switch players and set whether the computer should play next
    private void switchPlayer() {
        currentPlayer = (currentPlayer == Cell.X) ? Cell.O : Cell.X;
        computerPlays = playAgainstComputer && (currentPlayer == (playerIsX ? Cell.O : Cell.X));
    }

    // Reset the game board
    private void resetGame() {
        currentPlayer = playerIsX ? Cell.X : Cell.O;
        computerPlays = playAgainstComputer && !playerIsX;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Cell.EMPTY;
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }

        // If the computer plays first, make a move
        if (computerPlays && playAgainstComputer) {
            makeComputerMove();
        }
    }

    // Main method to start the game
    public static void main(String[] args) {
        new TicTacToeGUI2();
    }
}