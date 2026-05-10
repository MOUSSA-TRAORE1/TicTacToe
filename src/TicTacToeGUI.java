import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame implements ActionListener {

    // Enum to represent cell states
    private enum Cell { X, O, EMPTY }

    // 3x3 board for the game
    private Cell[][] board = new Cell[3][3];
    private JButton[][] buttons = new JButton[3][3];
    private Cell currentPlayer;

    // Constructor to set up the game
    public TicTacToeGUI() {
        // Initialize the game board with empty cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Cell.EMPTY;
            }
        }
        currentPlayer = Cell.X;

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
    }

    // Action performed when a button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        // Find the button's position on the grid
        int row = -1, col = -1;
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
            clickedButton.setEnabled(false); // Disable the button after move

            // Check if the game is won or drawn
            if (checkWinner()) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                resetGame();
            } else if (isDraw()) {
                JOptionPane.showMessageDialog(this, "The game is a draw!");
                resetGame();
            } else {
                // Switch players
                currentPlayer = (currentPlayer == Cell.X) ? Cell.O : Cell.X;
            }
        }
    }

    // Check if the current player has won
    private boolean checkWinner() {
        // Check rows, columns, and diagonals
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

    // Reset the game board
    private void resetGame() {
        currentPlayer = Cell.X;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Cell.EMPTY;
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }

    // Main method to start the game
    public static void main(String[] args) {
        new TicTacToeGUI();
    }
}