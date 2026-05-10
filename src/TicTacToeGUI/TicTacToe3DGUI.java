import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe3DGUI extends JFrame implements ActionListener {

    // Enum to represent cell states
    private enum Cell { X, O, EMPTY }

    // 4x4x4 board for the game
    private Cell[][][] board = new Cell[4][4][4];
    private JButton[][][] buttons = new JButton[4][4][4];
    private Cell currentPlayer;

    // Constructor to set up the game
    public TicTacToe3DGUI() {
        currentPlayer = Cell.X; // Start with player X

        // Initialize the game board with empty cells
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    board[i][j][k] = Cell.EMPTY;
                }
            }
        }

        // Set up JFrame settings
        setTitle("3D Tic-Tac-Toe Game (4x4x4)");
        setSize(800, 800);
        setLayout(new GridLayout(2, 2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create 4 separate panels, each representing one level of the board
        for (int level = 0; level < 4; level++) {
            JPanel panel = new JPanel(new GridLayout(4, 4));
            panel.setBorder(BorderFactory.createTitledBorder("Level " + (level + 1)));
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    buttons[level][i][j] = new JButton("");
                    buttons[level][i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                    buttons[level][i][j].setFocusPainted(false);
                    buttons[level][i][j].addActionListener(this);
                    panel.add(buttons[level][i][j]);
                }
            }
            add(panel); // Add each level to the main frame
        }

        setVisible(true);
    }

    // Action performed when a button is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        int level = -1, row = -1, col = -1;

        // Find the button's position on the 3D grid
        for (int l = 0; l < 4; l++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (buttons[l][i][j] == clickedButton) {
                        level = l;
                        row = i;
                        col = j;
                        break;
                    }
                }
            }
        }

        // Make the move if the cell is empty
        if (board[level][row][col] == Cell.EMPTY) {
            board[level][row][col] = currentPlayer;
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
                currentPlayer = (currentPlayer == Cell.X) ? Cell.O : Cell.X;
            }
        }
    }

    // Check if the current player has won
    private boolean checkWinner() {
        // Check for 4 in a row across all levels, rows, columns, and diagonals
        for (int level = 0; level < 4; level++) {
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    // Check horizontal, vertical, and depth directions
                    if (checkLine(level, row, col, 1, 0, 0) || // Horizontal
                            checkLine(level, row, col, 0, 1, 0) || // Vertical
                            checkLine(level, row, col, 0, 0, 1) || // Depth
                            checkLine(level, row, col, 1, 1, 0) || // Diagonal on same level
                            checkLine(level, row, col, 1, 0, 1) || // Diagonal across levels
                            checkLine(level, row, col, 0, 1, 1) || // Vertical diagonal across levels
                            checkLine(level, row, col, 1, 1, 1) || // 3D diagonal
                            checkLine(level, row, col, 1, -1, 1))   // Opposite 3D diagonal
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Helper method to check 4 cells in a specified direction
    private boolean checkLine(int level, int row, int col, int dL, int dR, int dC) {
        try {
            Cell start = board[level][row][col];
            if (start == Cell.EMPTY) return false;

            for (int i = 1; i < 4; i++) {
                if (board[level + i * dL][row + i * dR][col + i * dC] != start)
                    return false;
            }
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false; // If out of bounds, line is invalid
        }
    }

    // Check if the board is full
    private boolean isDraw() {
        for (int level = 0; level < 4; level++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (board[level][i][j] == Cell.EMPTY) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Reset the game board
    private void resetGame() {
        currentPlayer = Cell.X; // Reset to player X

        for (int level = 0; level < 4; level++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    board[level][i][j] = Cell.EMPTY;
                    buttons[level][i][j].setText("");
                    buttons[level][i][j].setEnabled(true);
                }
            }
        }
    }

    // Main method to start the game
    public static void main(String[] args) {
        new TicTacToe3DGUI();
    }
}
